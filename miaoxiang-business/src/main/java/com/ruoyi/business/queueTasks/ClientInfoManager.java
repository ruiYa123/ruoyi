package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.service.IClientService;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.CLIENTS_STATUS;
import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.IDLE_CLIENTS;


@Slf4j
@Component
public class ClientInfoManager {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IClientService clientService;

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
            Client client = new Client();
            client.setName(name);
            if (clientService.selectClient(client).getActive() == 0) {
                redisCache.addToSet(IDLE_CLIENTS.getKey(), name);
            }
        }
    }

    public void updateClientInfo(ClientStatus clientStatus) {
        redisCache.putObjectInHash(CLIENTS_STATUS.getKey(), clientStatus.getClient().getName(), clientStatus);
    }

    public ClientStatus getClientInfo(String clientName) {
        ClientStatus objectFromHash = new ClientStatus();
        try {
            objectFromHash = redisCache.getObjectFromHash(CLIENTS_STATUS.getKey(), clientName, ClientStatus.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return objectFromHash;
    }
}
