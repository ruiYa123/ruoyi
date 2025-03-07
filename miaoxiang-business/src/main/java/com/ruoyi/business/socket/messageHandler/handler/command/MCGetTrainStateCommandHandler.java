package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.domain.TrainLog;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.command.MCGetTrainStateCommand;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.GET_TRAIN_STATE;

@Slf4j
@Component
public class MCGetTrainStateCommandHandler extends AbstractMessageHandler {

    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Autowired
    private ITrainLogService trainLogService;

    @Scheduled(initialDelay = 2000, fixedRateString = "${socket.scheduling.rate}")
    public void requestTrainState() {
//        log.info("获取client训练状态");
        getClients().forEach(client -> {
            if (client.getState() == 2) {
                return;
            }
            MCGetTrainStateCommand request = new MCGetTrainStateCommand();
            request.setClientName(client.getName());
            socketService.sendMessageToClientByAddress(
                    client.getName(),
                    JsonUtil.toJson(request)
            );
        });
    }

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {

        MCGetTrainStateFeedBack response = JsonUtil.fromJson(jsonMessage, MCGetTrainStateFeedBack.class);
        Assignment assignment = new Assignment();
        assignment.setClientName(clientStatus.getClient().getName());
        List<Assignment> assignments = assignmentService.selectAssignmentList(assignment);
        if (!assignments.isEmpty()) {
            assignment = assignments.get(0);
            clientStatus.setAssignmentId(assignment.getId());
            clientStatus.setAssignmentName(assignment.getAssignmentName());
        }
        clientStatus.setMcGetTrainStateFeedBack(response);
        clientStatus.setAssignment(assignment);
        Long trainId = assignmentTrainService.updateTrain(assignment.getId(), clientStatus.getClient().getName(), BigDecimal.valueOf(response.getTrainState().getTrainPercentage()), 1);
        TrainLog trainLog = new TrainLog();
        trainLog.setAssignmentTrainId(trainId);
        trainLog.setAssignmentId(assignment.getId());
        trainLog.setContent(jsonMessage);
        trainLog.setCreateTime(DateUtils.getNowDate());
        trainLogService.insertTrainLog(trainLog);
        clientInfoManager.updateClientInfo(clientStatus);
        log.info("返回客户端训练进度信息: {}", jsonMessage);
        setClientLog(clientStatus.getClient().getIp(), clientStatus.getClient().getPort(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return GET_TRAIN_STATE.getCommandStr();
    }
}
