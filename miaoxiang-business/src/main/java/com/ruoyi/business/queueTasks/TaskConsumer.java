package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.socket.messageHandler.handler.command.MCStartTrainCommandHandler;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.IDLE_CLIENTS;
import static com.ruoyi.business.queueTasks.TaskQueue.*;
import static com.ruoyi.business.queueTasks.TaskQueue.TaskQueueRedisKey.TASK_QUEUE;

@Service
public class TaskConsumer {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Autowired
    private MCStartTrainCommandHandler mcStartTrainCommandHandler;

    @Autowired
    private IProjectService projectService;

    private int jumpTime = 0;


    /**
     * 开始消费任务
     */
    @Scheduled(initialDelay = 3000, fixedRateString = "${assignment.scheduling.rate}")
    public void consumeTask() {
        TASK_LOCK.lock();
        try {
            String clientName = redisCache.getRandomMemberFromSet(IDLE_CLIENTS.getKey());
            if (clientName != null) {
                List<Long> taskQueue = redisCache.getCacheList(TASK_QUEUE.getKey());
                if (!taskQueue.isEmpty()) {
                    try {
                        redisCache.removeFromSet(IDLE_CLIENTS.getKey(), clientName);
                        Long taskId = taskScheduling(taskQueue);  //任务调度
                        processTask(clientName, taskId);
                    } catch (Exception e) {
                        redisCache.addToSet(IDLE_CLIENTS.getKey(), clientName);
                    }
                }
            }
        } finally {
            TASK_LOCK.unlock();
        }
    }

    /**
     * 任务调度
     *
     * @param taskQueue 训练队列
     * @return 要训练的id
     */
    private Long taskScheduling(List<Long> taskQueue) {
        Long taskId = taskQueue.get(0);
        if (INTELLIGENT_SORT) {
            Assignment assignment = assignmentService.selectAssignmentById(taskId);
            int modelIndex = MODEL_TYPE.indexOf(assignment.getPretrainMode());

            if ((modelIndex > 1 && jumpTime <3)
                    || (modelIndex > 2 && jumpTime <5)
                    || (modelIndex > 3 && jumpTime <7)) {
                if (taskQueue.size() > SORT_LENGTH) {
                    taskQueue = taskQueue.subList(0, SORT_LENGTH);
                }
                List<Assignment> assignments =
                        assignmentService.selectAssignmentListByIds(new Assignment(), taskQueue.toArray(new Long[0]));
                Optional<Long> foundTask = assignments.stream()
                        .filter(e -> e.getPretrainMode().equals(MODEL_TYPE.get(0)))
                        .findFirst()
                        .map(Assignment::getId);
                if (foundTask.isPresent()) {
                    taskId = foundTask.get();
                    jumpTime ++;
                } else {
                    jumpTime = 0;
                }
            } else {
                jumpTime = 0;
            }
        }
        return taskId;
    }

    /**
     * 处理任务
     *
     * @param clientName 客户端名称
     * @param assignmentId       任务id
     */
    private void processTask(String clientName, Long assignmentId) {
        try {
            redisCache.removeFromQueue(TASK_QUEUE.getKey(), assignmentId);
            Assignment assignment = assignmentService.selectAssignmentById(assignmentId);
            assignment.setState(1);
            assignment.setClientName(clientName);
            assignmentService.updateAssignment(assignment);
            assignmentTrainService.startTrain(assignmentId, clientName);
            String projectName = projectService.selectProjectById(assignment.getProjectId()).getProjectName();
            mcStartTrainCommandHandler.startTrain(assignment, projectName, clientName);
        } catch (Exception e) {
            redisCache.addToQueue(TASK_QUEUE.getKey(), assignmentId, true);
        }

    }
}
