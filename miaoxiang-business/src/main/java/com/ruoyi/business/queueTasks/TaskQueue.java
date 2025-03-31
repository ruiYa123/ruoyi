package com.ruoyi.business.queueTasks;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue {

    public static final ReentrantLock TASK_LOCK = new ReentrantLock();

    public static final List<String> MODEL_TYPE = Arrays.asList("N", "S", "M", "L", "X");

    public static boolean INTELLIGENT_SORT = true;

    public static int SORT_LENGTH = 10;

    @Getter
    public enum TaskQueueRedisKey {
        TASK_QUEUE("TASK_QUEUE");

        private final String key;

        TaskQueueRedisKey(String key) {
            this.key = key;
        }
    }
}
