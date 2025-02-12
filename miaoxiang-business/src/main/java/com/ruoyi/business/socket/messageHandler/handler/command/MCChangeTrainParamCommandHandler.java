package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.socket.SocketService;
import com.ruoyi.business.socket.messageHandler.model.command.MCChangeTrainParamCommand;
import com.ruoyi.common.utils.JsonUtil;

public class MCChangeTrainParamCommandHandler {

    public void changeTrainParam(String projectName, Assignment assignment, Client client) {
        MCChangeTrainParamCommand mcChangeTrainParamCommand = new MCChangeTrainParamCommand();
        mcChangeTrainParamCommand.setProjectName(projectName + "~" + assignment.getAssignmentName());
        mcChangeTrainParamCommand.getTrainParam().setPreTrainModel(assignment.getPretrainMode());
        mcChangeTrainParamCommand.getTrainParam().setEpoch(assignment.getEpoch());
        mcChangeTrainParamCommand.getTrainParam().setBatchSize(assignment.getBatchSize());
        mcChangeTrainParamCommand.getTrainParam().setImgSize(assignment.getImgSize());
        SocketService.sendMessageToClientByAddress(client.getIp(), client.getPort(), JsonUtil.toJson(mcChangeTrainParamCommand));
    }

}
