package com.ruoyi.business.socket.messageHandler.handler.command;

import com.ruoyi.business.domain.request.MessageRequest;
import com.ruoyi.business.socket.service.SocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class TestCommandHandler {

    @Autowired
    private SocketService socketService;

    public boolean testCommand(MessageRequest messageRequest) {
        try {
            return socketService.sendMessageToClientByAddress(
                    messageRequest.getName(),
                    messageRequest.getJsonMessage()
            ).get();
        }  catch (InterruptedException | ExecutionException e) {
            log.error("发送消息时出现异常: {}", e.getMessage());
            return false;
        }
    }
}
