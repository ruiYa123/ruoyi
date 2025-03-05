package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.socket.service.SocketService;
import com.ruoyi.business.socket.messageHandler.model.command.MCChangeTrainParamCommand;
import com.ruoyi.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MCChangeTrainParamCommandHandler {

    @Autowired
    private SocketService socketService;

    public void changeTrainParam(String projectName, Assignment assignment, String clientName) {
        MCChangeTrainParamCommand mcChangeTrainParamCommand = new MCChangeTrainParamCommand();
        mcChangeTrainParamCommand.setProjectName(projectName);
        mcChangeTrainParamCommand.setAssignmentName(assignment.getAssignmentName());
        mcChangeTrainParamCommand.getTrainParam().setPreTrainModel(assignment.getPretrainMode());
        mcChangeTrainParamCommand.getTrainParam().setEpoch(assignment.getEpoch());
        mcChangeTrainParamCommand.getTrainParam().setBatchSize(assignment.getBatchSize());
        mcChangeTrainParamCommand.getTrainParam().setImgSize(assignment.getImgSize());
        socketService.sendMessageToClientByAddress(clientName, JsonUtil.toJson(mcChangeTrainParamCommand));
    }

}
