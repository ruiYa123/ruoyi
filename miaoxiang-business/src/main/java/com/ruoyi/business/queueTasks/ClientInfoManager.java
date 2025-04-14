package com.ruoyi.business.queueTasks;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.AssignmentTrain;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IClientService;
import com.ruoyi.business.socket.messageHandler.handler.command.MCGetTrainStateCommandHandler;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.ruoyi.business.domain.Client.StateEnum.ACTIVATE;
import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.*;
import static com.ruoyi.business.queueTasks.ClientInfoManager.ClientRedisKeys.getTrainProcessKey;


@Slf4j
@Component
public class ClientInfoManager {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Getter
    public enum ClientRedisKeys {
        CLIENTS_STATUS("CLIENTS_STATUS"),
        IDLE_CLIENTS("IDLE_CLIENTS"),
        TRAIN_PROCESS("TRAIN_PROCESS");

        private final String key;

        ClientRedisKeys(String key) {
            this.key = key;
        }

        static String getTrainProcessKey(String projectName, String assignmentName) {
            return TRAIN_PROCESS.key + ":"
                    + projectName + ":"
                    + assignmentName;
        }
    }


    public void registerClient(String... names) {
        for (String name : names) {
            Client client = new Client();
            client.setName(name);
            Assignment assignment = new Assignment();
            assignment.setClientName(client.getName());
            assignment.setState(1);
            List<Assignment> assignments = assignmentService.selectAssignmentList(assignment);
            if (!assignments.isEmpty()) {
                assignments.forEach(e -> {
                    e.setState(0);
                    e.setClientName(null);
                    assignmentService.updateAssignment(e);
                    assignmentTrainService.finishTrain(assignment.getId(), name, 0);
                });
            }
            if (clientService.selectClient(client).getActive() == ACTIVATE.getValue()) {
                redisCache.addToSet(IDLE_CLIENTS.getKey(), name);
            }

        }
    }

    public void removeClient(String... names) {
        for (String name : names) {
            redisCache.removeFromSet(IDLE_CLIENTS.getKey(), name);
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

    public void setProgressChart(MCGetTrainStateFeedBack mcGetTrainStateFeedBack) {
        MCGetTrainStateFeedBack.TrainState trainState = mcGetTrainStateFeedBack.getTrainState();
        log.info("trainstate: {}", trainState);
        if (trainState.getTrainProcess()
                .equals(MCGetTrainStateCommandHandler.TrainProcessStatus.TRAIN_MODEL.getValue())) {
            redisCache.setCacheMapValue(
                    getTrainProcessKey(trainState.getProjectName(), trainState.getAssignmentName()),
                    trainState.getTrainPercentage().toString(),
                    Arrays.asList(trainState.getLossCurve(), trainState.getPreCurve()));
        }
    }

    public Map<String, List<Double>> getProcessChart(String projectName, String assignmentName) {
        return redisCache.getCacheMap(getTrainProcessKey(projectName, assignmentName));
    }

    public void deleteProgressChart(String projectName, String assignmentName) {
        redisCache.deleteObject(getTrainProcessKey(projectName, assignmentName));
    }
}
