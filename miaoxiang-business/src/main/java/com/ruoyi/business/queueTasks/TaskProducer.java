package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.business.queueTasks.TaskConsumer.TaskQueueRedisKey.TASK_QUEUE;

@Service
public class TaskProducer {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IAssignmentService assignmentService;

//    @Scheduled(initialDelay = 0, fixedRateString = "${socket.scheduling.rate}")
    public List<Assignment> checkAndUpdateTasks() {

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
    }
    /**
     * 添加任务到队列
     *
     * @param task 任务内容
     * @param prioritize 是否优先（插队）
     */
    public void addTask(Long task, boolean prioritize) {
        redisCache.addToQueue(TASK_QUEUE.getKey(), task, prioritize);
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
}
