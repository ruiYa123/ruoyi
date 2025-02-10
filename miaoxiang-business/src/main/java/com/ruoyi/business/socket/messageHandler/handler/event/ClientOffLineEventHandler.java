package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.service.IClientService;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientOfflineEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_OFFLINE;

@Slf4j
@Component
public class ClientOffLineEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip, int port) {
        ClientOfflineEvent message = JsonUtil.fromJson(jsonMessage, ClientOfflineEvent.class);
        log.info("{} 处理客户端下线， 原因：{}", message.getName(), message.getReason());
        Client client = new Client();
        BeanUtils.copyProperties(message, client);
        clientService.offLineClient(client);
        setClientLog(message.getName(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_OFFLINE.getCommandStr();
    }
}
