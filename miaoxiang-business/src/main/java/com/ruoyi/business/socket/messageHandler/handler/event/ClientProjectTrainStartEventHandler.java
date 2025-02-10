package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientProjectTrainStartEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_PROJECT_TRAIN_START;

@Slf4j
@Component
public class ClientProjectTrainStartEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip, int port) {
        ClientProjectTrainStartEvent message = JsonUtil.fromJson(jsonMessage, ClientProjectTrainStartEvent.class);
        log.info("处理客户端开始训练: {}", message.getClientNames());
        setClientLog(message.getClientNames(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_PROJECT_TRAIN_START.getCommandStr();
    }
}
