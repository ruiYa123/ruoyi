package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.*;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.event.ClientErrorEventHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainStartEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStartTrainCommand;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_PROJECT_TRAIN_START;


@Slf4j
@Component
public class MCStartTrainCommandHandler extends AbstractMessageHandler {



    public void startTrain(Assignment assignment, String projectName, String clientName) {
        MCStartTrainCommand mcStartTrainCommand = new MCStartTrainCommand();
        mcStartTrainCommand.setClientName(clientName);
        mcStartTrainCommand.getTrainParam().setPreTrainModel(assignment.getPretrainMode());
        mcStartTrainCommand.getTrainParam().setEpoch(assignment.getEpoch());
        mcStartTrainCommand.getTrainParam().setBatchSize(assignment.getBatchSize());
        mcStartTrainCommand.getTrainParam().setImgSize(assignment.getImgSize());
        mcStartTrainCommand.setProjectName(projectName);
        mcStartTrainCommand.setAssignmentName(assignment.getAssignmentName());
        socketService.sendMessageToClientByAddress(clientName, JsonUtil.toJson(mcStartTrainCommand));
    }

    @Override
    public void handle(String json, ClientStatus clientStatus) {
        ClientProjectTrainStartEvent message = JsonUtil.fromJson(json, ClientProjectTrainStartEvent.class);
        log.info("处理客户端开始训练: {}", message.getClientNames());
        setTrainLog(json, clientStatus, message.getTrainPara().getProjectName(), message.getTrainPara().getAssignmentName());
        setClientLog(message.getClientNames(), json);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_START.getCommandStr();
    }
}
