package com.ruoyi.business.socket.Client;


import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.List;

@Component
public class ClientFactory {

    private final List<BaseMessageHandler> messageHandlers;

    @Autowired
    public ClientFactory(List<BaseMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public ClientHandler createClientHandler(Socket clientSocket) {
        return new ClientHandler(clientSocket, messageHandlers);
    }
}
