package com.ruoyi.business.socket;

import com.ruoyi.business.socket.Client.ClientFactory;
import com.ruoyi.business.socket.Client.ClientHandler;
import com.ruoyi.common.exception.UtilException;
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

@Slf4j
@Component
public class SocketService {
    private final ServerSocket serverSocket;
    @Autowired
    private ClientFactory clientFactory;
    private static final ConcurrentHashMap<String, PrintWriter> clientMap = new ConcurrentHashMap<>();


    public SocketService() {
        try {
            serverSocket = new ServerSocket(12345);
        } catch (IOException e) {
            throw new UtilException("Socket服务启动失败： " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        Thread thread = new Thread(this::start, "SOCKET-SERVER");
        thread.start();
    }

    @PreDestroy
    public void shutdown() {
        log.info("正在关闭SOCKET服务器...");
        try {
            serverSocket.close();
            clientMap.values().forEach(PrintWriter::close);
            clientMap.clear();
            log.info("SOCKET服务器已关闭。");
        } catch (IOException e) {
            log.error("关闭SOCKET服务器时出错: {}", e.getMessage());
        }
    }


    public void start() {
        log.info("SOCKET服务器已启动，等待客户端连接...");
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
            System.out.println("客户端已连接: " + clientKey);

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
        PrintWriter out = clientMap.get(clientKey);
        if (out != null) {
            out.println(message);
        } else {
            System.out.println("未找到指定客户端: " + clientKey);
        }
    }
}
