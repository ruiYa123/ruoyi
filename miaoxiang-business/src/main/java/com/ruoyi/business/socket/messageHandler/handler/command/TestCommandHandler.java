package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.request.MessageRequest;
import com.ruoyi.business.socket.SocketService;
import com.ruoyi.common.exception.UtilException;
import org.springframework.stereotype.Component;

@Component
public class TestCommandHandler {

    public boolean testCommand(MessageRequest messageRequest) {
        if (SocketService.sendMessageToClientByAddress(messageRequest.getIp(), messageRequest.getPort(), messageRequest.getJsonMessage())) {
            return true;
        } else {
            throw new UtilException("客户端连接超时");
        }
    }
}
