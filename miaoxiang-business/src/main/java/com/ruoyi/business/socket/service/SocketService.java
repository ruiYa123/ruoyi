package com.ruoyi.business.socket.service;

import com.ruoyi.business.socket.client.ClientFactory;
import com.ruoyi.business.socket.messageHandler.handler.event.ClientOffLineEventHandler;
import com.ruoyi.common.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

@Slf4j
@Component
public class SocketService {
    private ServerSocket serverSocket;
    @Autowired
    private ClientFactory clientFactory;
    @Autowired
    private ServiceRegistry serviceRegistry;

    @Value("${socket.port}")
    private int port;

    @PostConstruct
    public void init() {
        try {
            serverSocket = new ServerSocket(port);
            Thread thread = new Thread(this::start, "SOCKET-SERVER");
            thread.start();
        } catch (IOException e) {
            throw new UtilException("Socket服务启动失败： " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("正在关闭SOCKET服务...");
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            clientFactory.shutdown();
            log.info("SOCKET服务已关闭。");
        } catch (IOException e) {
            log.error("关闭SOCKET服务时出错: {}", e.getMessage(), e);
        }
    }

    public void start() {
        log.info("SOCKET服务({})已启动，等待客户端连接...", this.port);
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleNewClient(clientSocket);
            } catch (SocketException e) {
                if (!serverSocket.isClosed()) {
                    log.error("Socket异常: {}", e.getMessage(), e);
                }
            } catch (IOException e) {
                log.error("客户端连接失败: {}", e.getMessage(), e);
            }
        }
    }

    public void handleNewClient(Socket clientSocket) {
        try {
            clientFactory.createAndHandleClient(clientSocket);
        } catch (IOException e) {
            log.error("处理客户端时出错: {}", e.getMessage(), e);
            try {
                clientSocket.close();
            } catch (IOException ex) {
                log.error("关闭客户端Socket时出错: {}", ex.getMessage(), ex);
            }
        }
    }

    public CompletableFuture<Boolean> sendMessageToClientByAddress(String clientName, String message) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        scheduler.scheduleWithFixedDelay(new Runnable() {
            int attempt = 0;
            final int MAX_ATTEMPTS = 2;

            @Override
            public void run() {
                attempt++;
                PrintWriter out = serviceRegistry.getPrintWriter(clientName);
                if (out != null) {
                    out.println(message);
                    log.info("发送消息：{}", message);
                    result.complete(true);
                    scheduler.shutdown();
                } else if (attempt >= MAX_ATTEMPTS) {
//                    log.warn("未找到指定客户端: {} after {} attempts, message:{}", clientKey, MAX_ATTEMPTS, message);
                    ClientOffLineEventHandler clientOffLineEventHandler = new ClientOffLineEventHandler();
                    clientOffLineEventHandler.handleDisconnect(clientName);
                    result.complete(false);
                    scheduler.shutdown();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        return result;
    }
}
