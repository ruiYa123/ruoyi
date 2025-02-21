package com.ruoyi.business.socket;

import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.Client.ClientFactory;
import com.ruoyi.business.socket.Client.ClientHandler;
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

    @Value(value = "${socket.port}")
    private int port;

    @PostConstruct
    public void init() {
        try {
            serverSocket = new ServerSocket(port);
            Thread thread = new Thread(this::start, "SOCKET-SERVER");
            thread.start();
        } catch (IOException e) {
            throw new UtilException("Socket服务启动失败： " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("正在关闭SOCKET服务...");
        try {
            serverSocket.close();
            log.info("SOCKET服务已关闭。");
        } catch (IOException e) {
            log.error("关闭SOCKET服务时出错: {}", e.getMessage());
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
                    log.error("Socket异常: {}", e.getMessage());
                }
            } catch (IOException e) {
                log.error("客户端连接失败: {}", e.getMessage());
            }
        }
    }

    public void handleNewClient(Socket clientSocket) {
        try {
            String clientKey = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
            log.info("客户端已连接: {}", clientKey);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            ClientHandler clientHandler = clientFactory.createClientHandler(clientSocket);
            new Thread(clientHandler).start();
        } catch (IOException e) {
            System.err.println("处理客户端时出错: " + e.getMessage());
        }
    }

    public static boolean sendMessageToClientByAddress(String ip, int port, String message) {
        String clientKey = ip + ":" + port;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        CompletableFuture<Boolean> result = new CompletableFuture<>();

        scheduler.scheduleWithFixedDelay(new Runnable() {
            int attempt = 0;
            final int MAX_ATTEMPTS = 2;

            @Override
            public void run() {
                attempt++;
                PrintWriter out = null;
                ClientHandler clientHandler = ClientFactory.getClientHandler(clientKey);
                if (clientHandler != null) {
                    try {
                        out = clientHandler.getPrintWriter();
                    } catch (IOException e) {
                        log.error("处理客户端时出错: {}", e.getMessage());
                    }
                }
                if (out != null) {
                    out.println(message);
                    log.info("发送消息成功");
                    result.complete(true);
                    scheduler.shutdown();
                } else if (attempt >= MAX_ATTEMPTS) {
                    log.warn("未找到指定客户端: {} after {} attempts, message:{}", clientKey, MAX_ATTEMPTS, message);
                    ClientOffLineEventHandler clientOffLineEventHandler = new ClientOffLineEventHandler();
                    clientOffLineEventHandler.handleDisconnect(ip, port);
                    result.complete(false);
                    scheduler.shutdown();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("发送消息过程中出现异常: {}", e.getMessage());
            return false;
        }
    }

    public static ClientStatus getClientStatus(String ip, int port) {
        ClientHandler clientHandler = ClientFactory.getClientHandler(ip + ":" + port);
        if (clientHandler == null) {
            return new ClientStatus(ip, port);
        }
        return clientHandler.getClientStatus();
    }
}
