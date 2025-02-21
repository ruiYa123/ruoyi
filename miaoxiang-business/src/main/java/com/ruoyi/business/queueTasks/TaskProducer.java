package com.ruoyi.business.queueTasks;

import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addTask(String task, boolean prioritize) {
        redisCache.addToQueue("taskQueue", task, prioritize);
    }
}
