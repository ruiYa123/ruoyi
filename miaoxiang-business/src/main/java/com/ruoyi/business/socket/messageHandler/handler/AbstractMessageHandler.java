package com.ruoyi.business.socket.messageHandler.handler;

import com.ruoyi.business.domain.*;
import com.ruoyi.business.queueTasks.ClientInfoManager;
import com.ruoyi.business.service.*;
import com.ruoyi.business.socket.service.SocketService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public abstract class AbstractMessageHandler implements BaseMessageHandler {

    @Autowired
    protected IClientService clientService;

    @Autowired
    protected IClientLogService clientLogService;

    @Autowired
    protected ClientUpdater clientUpdater;

    @Autowired
    protected SocketService socketService;

    @Autowired
    protected ClientInfoManager clientInfoManager;

    @Autowired
    protected IAssignmentTrainService assignmentTrainService;
    @Autowired
    protected ITrainLogService trainLogService;
    @Autowired
    protected IAssignmentService assignmentService;
    @Autowired
    protected IProjectService projectService;

    @Override
    public abstract void handle(String json, ClientStatus clientStatus);

    @Override
    public abstract String getCommand();

    protected List<Client> getClients() {
        return clientUpdater.getClients();
    }

    protected void setClientLog(String ip, int port, String content) {
        Client client = clientService.selectClient(toClient(null, null, ip, port));
        buildClientLog(content, client);
    }

    protected void setClientLog(String name, String content) {
        Client client = clientService.selectClient(toClient(null, name, null, null));
        buildClientLog(content, client);
    }
    protected void setClientWarningLog(String name, String content) {
        Client client = clientService.selectClient(toClient(null, name, null, null));
        buildClientWarningLog(content, client);
    }


    protected void setClientLog(Long clientId, String content) {
        Client client = clientService.selectClient(toClient(clientId, null, null, null));
        buildClientLog(content, client);
    }

    private void buildClientLog(String content, Client client) {
        if (client != null) {
            ClientLog clientLog = new ClientLog();
            clientLog.setClientId(client.getId());
            clientLog.setCommandStr(getCommand());
            clientLog.setContent(content);
            clientLog.setCreateTime(new Date());
            clientLogService.insertClientLog(clientLog);
        }
    }

    private void buildClientWarningLog(String content, Client client) {
        if (client != null) {
            ClientLog clientLog = new ClientLog();
            clientLog.setClientId(client.getId());
            clientLog.setCommandStr(getCommand());
            clientLog.setContent(content);
            clientLog.setState(1L);
            clientLog.setCreateTime(new Date());
            clientLogService.insertClientLog(clientLog);
        }
    }

    protected Client toClient(Long clientId, String name, String ip, Integer port) {
        Client client = new Client();
        if (clientId != null) {
            client.setId(clientId);
        }
        if (name != null) {
            client.setName(name);
        }
        if (ip != null) {
            client.setIp(ip);
        }
        if (port != null) {
            client.setPort(port);
        }
        return client;
    }

    protected void setTrainLog(String jsonMessage, ClientStatus clientStatus, String projectName, String assignmentName) {
        Project project = new Project();
        project.setProjectName(projectName);
        Long projectId = projectService.selectProjectList(project).get(0).getId();
        Assignment assignment = new Assignment();
        assignment.setClientName(clientStatus.getClient().getName());
        assignment.setProjectId(projectId);
        assignment.setAssignmentName(assignmentName);
        List<Assignment> assignments = assignmentService.selectAssignmentList(assignment);
        if (!assignments.isEmpty()) {
            assignment = assignments.get(0);
//            clientStatus.setAssignment(assignment);
        }
        Long trainId = assignmentTrainService.selectAssignmentTrainById(assignment.getId()).getId();
        TrainLog trainLog = new TrainLog();
        trainLog.setAssignmentTrainId(trainId);
        trainLog.setAssignmentId(assignment.getId());
        trainLog.setContent(jsonMessage);
        trainLog.setCreateTime(DateUtils.getNowDate());
        trainLogService.insertTrainLog(trainLog);
    }
}
