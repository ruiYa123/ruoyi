package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientAddEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_ADD;

@Slf4j
@Component
public class ClientAddEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip, int port) {
        ClientAddEvent message = JsonUtil.fromJson(jsonMessage, ClientAddEvent.class);
        message.setIp(ip);
        message.setPort(port);
        log.info("{} 客户端上线，状态：{}", message.getName(), message.getState());
        Client client = new Client();
        BeanUtils.copyProperties(message, client);
        Long clientId = clientService.addClient(client);
        setClientLog(clientId, jsonMessage);
        clientUpdater.updateClients();
    }

    @Override
    public String getCommand() {
        return CLIENT_ADD.getCommandStr();
    }
}
