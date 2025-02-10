package com.ruoyi.business.socket.messageHandler.handler;

public interface BaseMessageHandler {
    void handle(String json, String ip, int port);
    String getCommand();
}
