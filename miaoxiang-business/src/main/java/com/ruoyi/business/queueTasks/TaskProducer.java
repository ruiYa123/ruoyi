package com.ruoyi.business.queueTasks;

import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ruoyi.business.queueTasks.TaskConsumer.TaskQueueRedisKey.TASK_QUEUE;

@Service
public class TaskProducer {

    @Autowired
    private RedisCache redisCache;

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
//        redisCache.removeFromQueue(TASK_QUEUE.getKey(), "zxc dsaf");
//        redisCache.removeFromQueue(TASK_QUEUE.getKey(), "vasdv");
//        redisCache.removeFromQueue(TASK_QUEUE.getKey(), "123dvasd");
//        redisCache.addToQueue(TASK_QUEUE.getKey(), 35L, true);
//        redisCache.addToQueue(TASK_QUEUE.getKey(), 30L, true);
//        redisCache.addToQueue(TASK_QUEUE.getKey(), 43L, true);
        return tasks.toArray(new Long[0]);
    }

    public void removeTask(Long task) {
        redisCache.removeFromQueue(TASK_QUEUE.getKey(), task);
    }
}
