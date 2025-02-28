package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.socket.messageHandler.handler.command.MCStartTrainCommandHandler;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.IDLE_CLIENTS;
import static com.ruoyi.business.queueTasks.TaskConsumer.TaskQueueRedisKey.TASK_QUEUE;

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


    @Getter
    public enum TaskQueueRedisKey {
        TASK_QUEUE("TASK_QUEUE");

        private final String key;

        TaskQueueRedisKey(String key) {
            this.key = key;
        }
    }

    /**
     * 开始消费任务
     */
    @Scheduled(initialDelay = 3000, fixedRateString = "${assignment.scheduling.rate}")
    public void consumeTask() {
        String clientName = redisCache.getRandomMemberFromSet(IDLE_CLIENTS.getKey());
        if (clientName != null) {
            Long assignmentId = redisCache.popFromQueue(TASK_QUEUE.key);
            if (assignmentId != null) {
                try {
                    redisCache.removeFromSet(IDLE_CLIENTS.getKey(), clientName);
                    processTask(clientName, assignmentId);
                } catch (Exception e) {
                    redisCache.addToQueue(TASK_QUEUE.getKey(), assignmentId, true);
                    redisCache.addToSet(IDLE_CLIENTS.getKey(), clientName);
                }
            }
        }
    }

    /**
     * 处理任务
     *
     * @param clientName 客户端名称
     * @param assignmentId       任务id
     */
    private void processTask(String clientName, Long assignmentId) {
        Assignment assignment = assignmentService.selectAssignmentById(assignmentId);
        assignment.setState(1);
        assignment.setClientName(clientName);
        assignmentService.updateAssignment(assignment);
        assignmentTrainService.startTrain(assignmentId, clientName);
        mcStartTrainCommandHandler.startTrain(assignment, clientName);
    }
}
