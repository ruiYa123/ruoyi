package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.socket.messageHandler.handler.AbstractMessageHandler;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientErrorEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_ERROR;

@Slf4j
@Component
public class ClientErrorEventHandler extends AbstractMessageHandler {

    @Override
    public void handle(String jsonMessage, String ip, int port) {
        ClientErrorEvent message = JsonUtil.fromJson(jsonMessage, ClientErrorEvent.class);
        log.warn("处理异常报警信息: {}, 错误信息: {}", message.getErrorMsg().getClientNames(), message.getErrorMsg().getErrorText());
        setClientLog(message.getErrorMsg().getClientNames(), jsonMessage);
    }

    @Override
    public String getCommand() {
        return CLIENT_ERROR.getCommandStr();
    }
}
