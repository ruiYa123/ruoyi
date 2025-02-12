package com.ruoyi.business.socket.messageHandler.handler;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public interface BaseMessageHandler {
    void handle(String json, String ip, int port);
    String getCommand();
}
