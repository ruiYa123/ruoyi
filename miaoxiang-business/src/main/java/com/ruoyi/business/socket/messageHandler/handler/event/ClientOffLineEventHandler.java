package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientOfflineEvent;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_OFFLINE;

@Slf4j
public class ClientOffLineEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, ClientStatus clientStatus) {
        ClientOfflineEvent message = JsonUtil.fromJson(jsonMessage, ClientOfflineEvent.class);
        log.info("{} 处理客户端下线， 原因：{}", message.getName(), message.getReason());
        Client client = new Client();
        BeanUtils.copyProperties(message, client);
        clientService.offLineClient(client);
        setClientLog(message.getName(), jsonMessage);
    }

    public void handleDisconnect(String name) {
        try {
            Client client = new Client();
            client.setName(name);
            clientService.offLineClient(client);
        } catch (Exception e) {
            throw new UtilException(e);
        }

    }

    @Override
    public String getCommand() {
        return CLIENT_OFFLINE.getCommandStr();
    }
}
