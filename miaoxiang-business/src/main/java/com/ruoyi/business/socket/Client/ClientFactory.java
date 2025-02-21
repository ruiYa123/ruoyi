package com.ruoyi.business.socket.Client;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ClientFactory {

    private final List<BaseMessageHandler> messageHandlers;
    private static final ConcurrentHashMap<String, ClientHandler> clientMap = new ConcurrentHashMap<>();

    @Autowired
    public ClientFactory(List<BaseMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public ClientHandler createClientHandler(Socket clientSocket) {
        String clientKey = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
        ClientHandler clientHandler = new ClientHandler(clientSocket, messageHandlers);
        clientMap.put(clientKey, clientHandler);
        return clientHandler;
    }

    public static void removeClient(String clientKey) {
        clientMap.remove(clientKey);
    }

    public static ClientHandler getClientHandler(String clientKey) {
        return clientMap.get(clientKey);
    }
}
