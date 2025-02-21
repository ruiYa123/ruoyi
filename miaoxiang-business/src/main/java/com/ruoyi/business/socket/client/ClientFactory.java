package com.ruoyi.business.socket.client;

import com.ruoyi.business.socket.service.ServiceRegistry;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ClientFactory {

    private final List<BaseMessageHandler> messageHandlers;
    private final ExecutorService clientThreadPool = Executors.newFixedThreadPool(15);
    private final ServiceRegistry serviceRegistry;

    @Autowired
    public ClientFactory(List<BaseMessageHandler> messageHandlers, ServiceRegistry serviceRegistry) {
        this.messageHandlers = messageHandlers;
        this.serviceRegistry = serviceRegistry;
    }

    public void createAndHandleClient(Socket clientSocket) throws IOException {
        log.info("客户端：{} : {};已连接", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
        ClientHandler clientHandler = new ClientHandler(clientSocket, messageHandlers, serviceRegistry);
        clientThreadPool.submit(clientHandler);
    }

    public void shutdown() {
        if (!clientThreadPool.isShutdown()) {
            clientThreadPool.shutdownNow();
        }
    }
}




