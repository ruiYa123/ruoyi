package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientOfflineEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientOffLineEventHandler implements BaseMessageHandler {
    @Override
    public void handle(String jsonMessage) {
        ClientOfflineEvent message = JsonUtil.fromJson(jsonMessage, ClientOfflineEvent.class);
        log.info("处理客户端下线: {}， {}", message.getName(), message.getReason());
    }

    @Override
    public String getCommand() {
        return "Client_Offline";
    }
}
