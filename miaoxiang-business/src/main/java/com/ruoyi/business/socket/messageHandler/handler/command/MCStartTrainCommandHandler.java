package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainStartEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStartTrainCommand;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_PROJECT_TRAIN_START;


@Slf4j
@Component
public class MCStartTrainCommandHandler extends AbstractMessageHandler {

    public void startTrain(Assignment assignment, String clientName) {
        MCStartTrainCommand mcStartTrainCommand = new MCStartTrainCommand();
        mcStartTrainCommand.setClientName(clientName);
        mcStartTrainCommand.getTrainParam().setPreTrainModel(assignment.getPretrainMode());
        mcStartTrainCommand.getTrainParam().setEpoch(assignment.getEpoch());
        mcStartTrainCommand.getTrainParam().setBatchSize(assignment.getBatchSize());
        mcStartTrainCommand.getTrainParam().setImgSize(assignment.getImgSize());
        mcStartTrainCommand.setProjectName(assignment.getAssignmentName());
        socketService.sendMessageToClientByAddress(clientName, JsonUtil.toJson(mcStartTrainCommand));
    }

    @Override
    public void handle(String json, ClientStatus clientStatus) {
        ClientProjectTrainStartEvent message = JsonUtil.fromJson(json, ClientProjectTrainStartEvent.class);
        log.info("处理客户端开始训练: {}", message.getClientNames());
        setClientLog(message.getClientNames(), json);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_START.getCommandStr();
    }
}
