package com.ruoyi.business.socket.Client;

import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ClientFactory {

    private final List<BaseMessageHandler> messageHandlers;
    private static final ConcurrentHashMap<String, ClientHandler> clientMap = new ConcurrentHashMap<>();
    private final ExecutorService clientThreadPool = Executors.newCachedThreadPool(); // 在工厂中管理线程池

    @Autowired
    public ClientFactory(List<BaseMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public void createAndHandleClient(Socket clientSocket) throws IOException {
        String clientKey = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
        ClientHandler clientHandler = new ClientHandler(clientSocket, messageHandlers);
        clientMap.put(clientKey, clientHandler);
        clientThreadPool.submit(clientHandler); // 使用线程池处理客户端
    }

    public static void removeClient(String clientKey) {
        clientMap.remove(clientKey);
    }

    public static ClientHandler getClientHandler(String clientKey) {
        return clientMap.get(clientKey);
    }

    public void shutdown() {
        if (!clientThreadPool.isShutdown()) {
            clientThreadPool.shutdownNow();
        }
    }
}

