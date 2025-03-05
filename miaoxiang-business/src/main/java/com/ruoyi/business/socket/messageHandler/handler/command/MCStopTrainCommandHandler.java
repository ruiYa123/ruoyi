package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.*;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainEndEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStopTrainCommand;
import com.ruoyi.common.utils.DateUtils;
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
    private IAssignmentService assignmentService;

    @Autowired
    private IAssignmentTrainService assignmentTrainService;

    @Autowired
    private ITrainLogService trainLogService;

    @Autowired
    private IProjectService projectService;

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
        Project project = new Project();
        project.setProjectName(message.getProjectName());
        Long projectId = projectService.selectProjectList(project).get(0).getId();
        assignment.setProjectId(projectId);
        assignment.setAssignmentName(message.getAssignmentName());
        assignment.setClientName(message.getName());
        List<Assignment> assignments = assignmentService.selectAssignmentList(assignment);
        assignment = assignments.get(0);
        assignment.setClientName(null);
        assignment.setState(0);
        assignmentService.updateAssignment(assignment);
        int state = 2;
        if (message.getTrainPara().getTrainComplete() == 0) {
            state = 0;
        }
        Long trainId = assignmentTrainService.finishTrain(assignment.getId(), message.getName(), state);
        TrainLog trainLog = new TrainLog();
        trainLog.setAssignmentTrainId(trainId);
        trainLog.setAssignmentId(assignment.getId());
        trainLog.setContent(jsonMessage);
        trainLog.setCreateTime(DateUtils.getNowDate());
        trainLogService.insertTrainLog(trainLog);

        log.info("处理客户端训练完成: {}", message.getName());
        setClientLog(message.getName(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_END.getCommandStr();
    }
}
