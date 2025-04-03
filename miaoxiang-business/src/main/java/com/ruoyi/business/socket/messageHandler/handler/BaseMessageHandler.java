package com.ruoyi.business.socket.messageHandler.handler;

import com.ruoyi.business.domain.ClientStatus;

public interface BaseMessageHandler {
    void handle(String json, ClientStatus clientStatus);
    String getCommand();
}
