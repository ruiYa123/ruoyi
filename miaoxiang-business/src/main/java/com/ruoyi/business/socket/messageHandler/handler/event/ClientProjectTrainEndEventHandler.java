package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainEndEvent;
import com.ruoyi.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientProjectTrainEndEventHandler implements BaseMessageHandler {

    @Override
    public void handle(String jsonMessage) {
        ClientProjectTrainEndEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainEndEvent.class);
        log.info("处理客户端训练完成: {}", message.getName());
    }

    @Override
    public String getCommand() {
        return "Client_ProjectTrainEnd";
    }
}
