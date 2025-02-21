package com.ruoyi.business.socket.messageHandler.handler;

import com.ruoyi.business.domain.ClientStatus;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public interface BaseMessageHandler {
    void handle(String json, ClientStatus clientStatus);
    String getCommand();
}
