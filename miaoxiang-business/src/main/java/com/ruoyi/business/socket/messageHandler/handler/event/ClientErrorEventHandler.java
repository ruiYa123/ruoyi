package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Assignment;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.domain.Project;
import com.ruoyi.business.domain.TrainLog;
import com.ruoyi.business.service.IAssignmentService;
import com.ruoyi.business.service.IAssignmentTrainService;
import com.ruoyi.business.service.IProjectService;
import com.ruoyi.business.service.ITrainLogService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientErrorEvent;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_ERROR;

@Slf4j
@Component
public class ClientErrorEventHandler extends AbstractMessageHandler {

    @Autowired
    private IAssignmentTrainService assignmentTrainService;
    @Autowired
    private ITrainLogService trainLogService;
    @Autowired
    private IAssignmentService assignmentService;
    @Autowired
    private IProjectService projectService;

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientErrorEvent message = JsonUtil.fromJson(jsonMessage, ClientErrorEvent.class);
        log.warn("处理异常报警信息: {}, 错误信息: {}", message.getErrorMsg().getClientNames(), message.getErrorMsg().getErrorText());
        setTrainLog(jsonMessage, clientStatus, message.getErrorMsg().getProjectName(), message.getErrorMsg().getAssignmentName());
        setClientWarningLog(message.getErrorMsg().getClientNames(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_ERROR.getCommandStr();
    }
}
