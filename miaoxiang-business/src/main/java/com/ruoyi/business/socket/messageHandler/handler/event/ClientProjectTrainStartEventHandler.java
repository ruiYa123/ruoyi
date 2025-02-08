package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainStartEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientProjectTrainStartEventHandler implements BaseMessageHandler {

    @Override
    public void handle(String jsonMessage) {
        ClientProjectTrainStartEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainStartEvent.class);
        log.info("处理客户端开始训练: {}", message.getClientNames());
    }

    @Override
    public String getCommand() {
        return "Client_ProjectTrainStart";
    }
}
