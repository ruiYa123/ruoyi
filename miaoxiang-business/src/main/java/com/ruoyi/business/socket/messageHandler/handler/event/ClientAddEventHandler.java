package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.command.MCGetClientStateCommandHandler;
import com.ruoyi.business.socket.messageHandler.handler.command.MCGetTrainStateCommandHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientAddEvent;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.domain.Client.StateEnum.ACTIVATE;
import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_ADD;

@Slf4j
@Component
public class ClientAddEventHandler extends AbstractMessageHandler {

    @Autowired
    MCGetClientStateCommandHandler mcGetClientStateCommandHandler;

    @Autowired
    MCGetTrainStateCommandHandler mcGetTrainStateCommandHandler;

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientAddEvent message = JsonUtil.fromJson(jsonMessage, ClientAddEvent.class);
        if (message.getState() == ACTIVATE.getValue()) {
            clientInfoManager.registerClient(message.getName());
        }
        log.info("{} 客户端上线，状态：{}", message.getName(), message.getState());
        Client client = new Client();
        BeanUtils.copyProperties(message, client);
        clientService.addClient(client);
        setClientLog(client.getName(), jsonMessage);
        ClientStatus clientInfo = clientInfoManager.getClientInfo(client.getName());
        if (clientInfo != null) {
            BeanUtils.copyProperties(clientInfo, clientStatus);
        }
        clientStatus.getClient().setIp(message.getIp());
        clientStatus.getClient().setPort(message.getPort());
        clientInfoManager.updateClientInfo(clientStatus);
        clientUpdater.updateClients();
        mcGetClientStateCommandHandler.request(client.getName());
        mcGetTrainStateCommandHandler.request(client.getName());
    }

    @Override
    public String getCommand() {
        return CLIENT_ADD.getCommandStr();
    }
}
