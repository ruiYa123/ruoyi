package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.CLIENTS_STATUS;
import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.IDLE_CLIENTS;


@Component
public class ClientInfoManager {

    @Autowired
    private RedisCache redisCache;

    @Getter
    public enum ClientRedisKeys {
        CLIENTS_STATUS("CLIENTS_STATUS"),
        IDLE_CLIENTS("IDLE_CLIENTS");

        private final String key;

        ClientRedisKeys(String key) {
            this.key = key;
        }
    }


    public void registerClient(String... names) {
        for (String name : names) {
            redisCache.addToSet(IDLE_CLIENTS.getKey(), name);
        }
    }

    public void updateClientInfo(ClientStatus clientStatus) {
        redisCache.putObjectInHash(CLIENTS_STATUS.getKey(), clientStatus.getName(), clientStatus);
    }

    public ClientStatus getClientInfo(String clientName) {
        ClientStatus objectFromHash = redisCache.getObjectFromHash(CLIENTS_STATUS.getKey(), clientName, ClientStatus.class);
        if (objectFromHash != null) {
            return objectFromHash;
        } else {
            return new ClientStatus();
        }
    }
}
