package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.business.queueTasks.TaskQueue.TaskQueueRedisKey.TASK_QUEUE;
import static com.ruoyi.business.queueTasks.TaskQueue.TASK_LOCK;

@Service
public class TaskProducer {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

//    @Scheduled(initialDelay = 0, fixedRateString = "${socket.scheduling.rate}")
    public List<Assignment> checkAndUpdateTasks() {
        TASK_LOCK.lock();
        try {
            List<Assignment> assignments = getAssignments();
            Set<Long> validAssignmentIds = assignments.stream()
                    .map(Assignment::getId)
                    .collect(Collectors.toSet());

            Long[] tasks = getTask();
            Set<Long> currentTaskIds = new HashSet<>(Arrays.asList(tasks));

            // 检查并移除无效任务
            for (Long task : tasks) {
                if (!validAssignmentIds.contains(task)) {
                    removeTask(task);
                }
            }

            // 检查并添加缺失的任务
            for (Long validId : validAssignmentIds) {
                if (!currentTaskIds.contains(validId)) {
                    addTask(validId, false);
                }
            }
            Long[] taskIds = getTask();
            if (taskIds.length == 0) {
                return new ArrayList<>(); // 如果没有任务，返回空列表
            }

            // 根据任务 ID 获取 Assignment 对象
            return assignmentService.selectAssignmentListByIds(new Assignment(), taskIds);
        } finally {
            TASK_LOCK.unlock();
        }
    }
    /**
     * 添加任务到队列
     *
     * @param task 任务内容
     * @param prioritize 是否优先（插队）
     */
    public void addTask(Long task, boolean prioritize) {
        TASK_LOCK.lock(); // 加锁
        try {
            if (userService.selectUserRoleGroup(SecurityUtils.getLoginUser().getUsername()).contains("超级管理员") || !prioritize) {
                redisCache.addToQueue(TASK_QUEUE.getKey(), task, prioritize);
            } else {
                Assignment assignment = new Assignment();
                assignment.setDept(getDept());
                Long[] tasks = getTask();
                Long id = assignmentService.selectAssignmentListByIds(assignment, tasks).get(0).getId();
                moveTaskAfter(task, id);
            }
        } finally {
            TASK_LOCK.unlock(); // 解锁

        }
    }

    public void moveTaskAfter(Long taskToMove, Long targetTask) {
        List<Long> tasks = redisCache.getCacheList(TASK_QUEUE.getKey());
        if (tasks != null && tasks.contains(targetTask)) {
            tasks.remove(taskToMove);
            int targetIndex = tasks.indexOf(targetTask);
            tasks.add(targetIndex + 1, taskToMove);
            redisCache.setCacheList(TASK_QUEUE.getKey(), tasks);
        } else {
            redisCache.addToQueue(TASK_QUEUE.getKey(), taskToMove, false);
        }
    }

    public Long[] getTask() {
        List<Long> tasks = redisCache.getCacheList(TASK_QUEUE.getKey());
        return tasks.toArray(new Long[0]);
    }

    public void removeTask(Long task) {
        redisCache.removeFromQueue(TASK_QUEUE.getKey(), task);
    }

    private List<Assignment> getAssignments() {
        Assignment assignment = new Assignment();
        assignment.setState(2);
        return assignmentService.selectAssignmentList(assignment);
    }

    private Long getDept() {
        if(!userService.selectUserRoleGroup(SecurityUtils.getLoginUser().getUsername()).contains("超级管理员")) {
            String ancestors = deptService.selectDeptById(SecurityUtils.getLoginUser().getDeptId()).getAncestors();
            List<Long> deptList = Arrays.stream(ancestors.split(","))
                    .map(Long::parseLong) // 将字符串转换为整数
                    .collect(Collectors.toList());
            if (deptList.size() > 1) {
                return deptList.get(1);
            } else {
                return SecurityUtils.getLoginUser().getDeptId();
            }
        }
        return null;
    }
}
