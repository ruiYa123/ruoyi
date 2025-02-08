package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientErrorEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientErrorEventHandler implements BaseMessageHandler {

    @Override
    public void handle(String jsonMessage) {
        ClientErrorEvent message = JsonUtil.fromJson(jsonMessage, ClientErrorEvent.class);
        log.error("处理异常报警信息: {}, 错误信息: {}", message.getErrorMsg().getClientNames(), message.getErrorMsg().getErrorText());
    }

    @Override
    public String getCommand() {
        return "Client_Error";
    }
}
