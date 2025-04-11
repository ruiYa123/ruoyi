package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.*;
import com.ruoyi.business.notification.email.model.Email;
import com.ruoyi.business.notification.email.service.EmailService;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainEndEvent;
import com.ruoyi.business.socket.messageHandler.model.command.MCStopTrainCommand;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtil;
import com.ruoyi.system.service.ISysUserService;
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
    private MCGetTrainStateCommandHandler getTrainStateCommandHandler;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ISysUserService userService;


    @Autowired
    private IProjectService projectService;

    public void stopTrain(String clientName) {
        MCStopTrainCommand request = new MCStopTrainCommand();
        request.setClientName(clientName);
        socketService.sendMessageToClientByAddress(clientName, JsonUtil.toJson(request));
    }
    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientProjectTrainEndEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainEndEvent.class);
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
        int state = 0;
        if (message.getTrainPara().getTrainComplete() == 1) {
            state = 2;
        }
        try {
            String userName = (assignment.getUpdateBy() != null) ? assignment.getUpdateBy() : assignment.getCreateBy();
            SysUser sysUser = userService.selectUserByUserName(userName);
            if (sysUser.getEmail() != null) {
                Email email = new Email();
                email.setSendTo(sysUser.getEmail());
                email.setSubject("深度云训练平台任务" + (state == 0 ? "完成" : "中断"));
                email.setContent("您的训练任务：" + assignment.getAssignmentName() + "已" + (state == 0 ? "完成" : "中断") + "， 请及时查看");
                emailService.sendEmail(email);
            }
        } catch (Exception e) {
            log.error("发送邮件失败: {}", e.getMessage());
        }

        assignment.setState(0);
        assignmentService.updateAssignment(assignment);
        assignmentTrainService.finishTrain(assignment.getId(), message.getName(), state);
        setTrainLog(jsonMessage, clientStatus, message.getProjectName(), message.getAssignmentName());

        log.info("处理客户端训练完成: {}", message.getName());
        setClientLog(message.getName(), jsonMessage);
        setTrainLog(jsonMessage, clientStatus, message.getProjectName(), message.getAssignmentName());
        getTrainStateCommandHandler.request(message.getName());
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_END.getCommandStr();
    }
}
