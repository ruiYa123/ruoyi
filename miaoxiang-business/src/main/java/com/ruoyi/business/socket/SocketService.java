package com.ruoyi.business.socket;

import com.ruoyi.business.socket.Client.ClientFactory;
import com.ruoyi.business.socket.Client.ClientHandler;
import com.ruoyi.business.socket.messageHandler.handler.event.ClientOffLineEventHandler;
import com.ruoyi.common.exception.UtilException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SocketService {
    private ServerSocket serverSocket;
    @Autowired
    private ClientFactory clientFactory;
    private static final ConcurrentHashMap<String, PrintWriter> clientMap = new ConcurrentHashMap<>();

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
            clientMap.values().forEach(PrintWriter::close);
            clientMap.clear();
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
            addClient(clientKey, out);

            ClientHandler clientHandler = clientFactory.createClientHandler(clientSocket);
            new Thread(clientHandler).start();
        } catch (IOException e) {
            System.err.println("处理客户端时出错: " + e.getMessage());
        }
    }

    public void addClient(String clientKey, PrintWriter out) {
        clientMap.put(clientKey, out);
    }

    public static void removeClient(String clientKey) {
        clientMap.remove(clientKey);
    }

    public static void sendMessageToClientByAddress(String ip, int port, String message) {
        String clientKey = ip + ":" + port;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(new Runnable() {
            int attempt = 0;
            final int maxAttempts = 3;

            @Override
            public void run() {
                attempt++;
                PrintWriter out = clientMap.get(clientKey);
                if (out != null) {
                    out.println(message);
                    scheduler.shutdown();
                } else if (attempt >= maxAttempts) {
//                    log.info("未找到指定客户端: {} after {} attempts", clientKey, maxAttempts);
                    ClientOffLineEventHandler clientOffLineEventHandler = new ClientOffLineEventHandler();
                    clientOffLineEventHandler.handleDisconnect(ip, port);
                    scheduler.shutdown();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

    }
}

