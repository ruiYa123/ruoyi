package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainEndEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStopTrainCommand;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_PROJECT_TRAIN_END;

@Slf4j
@Component
public class MCStopTrainCommandHandler extends AbstractMessageHandler {

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    IAssignmentTrainService assignmentTrainService;

    public void stopTrain(Client client) {
        MCStopTrainCommand request = new MCStopTrainCommand();
        request.setClientName(client.getName());
        socketService.sendMessageToClientByAddress(client.getName(), JsonUtil.toJson(request));
    }
    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientProjectTrainEndEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainEndEvent.class);
//        clientInfoManager.registerClient(message.getName());
        Assignment assignment = new Assignment();
        assignment.setAssignmentName(message.getProjectName());
        assignment.setClientName(message.getName());
        List<Assignment> assignments = assignmentService.selectAssignmentList(assignment);
        assignment = assignments.get(0);
        assignment.setClientName(null);
        assignment.setState(0);
        assignmentService.updateAssignment(assignment);
        Integer state;
        if (message.getTrainPara().getTrainComplete() == 0) {
            state = 0;
        } else {
            state = 2;
        }
        assignmentTrainService.finishTrain(assignment.getId(), message.getName(), state);
        log.info("处理客户端训练完成: {}", message.getName());
        setClientLog(message.getName(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_END.getCommandStr();
    }
}
