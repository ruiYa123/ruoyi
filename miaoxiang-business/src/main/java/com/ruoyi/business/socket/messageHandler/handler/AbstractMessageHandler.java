package com.ruoyi.business.socket.messageHandler.handler;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientLog;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.service.IClientLogService;
import com.ruoyi.business.service.IClientService;
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
}
