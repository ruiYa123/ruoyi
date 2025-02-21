package com.ruoyi.business.socket.Client;

import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.response.ErrorResponse;
import com.ruoyi.common.utils.JsonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClientHandler extends Thread {
    private final Socket clientSocket;
    @Getter
    private final PrintWriter printWriter;
    private final Map<String, BaseMessageHandler> messageHandlerMap = new HashMap<>();
    @Getter
    private final ClientStatus clientStatus;
    private final ClientFactory clientFactory;

    public ClientHandler(Socket socket, List<BaseMessageHandler> messageHandlers, ClientFactory clientFactory) throws IOException {
        this.clientSocket = socket;
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.clientStatus = new ClientStatus(socket.getInetAddress().getHostAddress(), socket.getPort());
        this.clientFactory = clientFactory; // 通过构造函数传入
        for (BaseMessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getCommand(), handler);
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                log.info("Received JSON data from client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), inputLine);
                handleClientMessage(inputLine);
            }
        } catch (IOException e) {
            if ("Socket closed".equals(e.getMessage())) {
                log.info("Socket客户端已关闭: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort());
            } else {
                log.warn("IOException occurred while reading input for client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), e.getMessage());
            }
        } finally {
            cleanup();
        }
    }

    private void handleClientMessage(String jsonData) {
        try {
            String commandStr = JsonUtil.getField(jsonData, "CommandStr").toString();
            BaseMessageHandler handler = messageHandlerMap.get(commandStr);
            if (handler != null) {
                handler.handle(jsonData, this.clientStatus);
            } else {
                log.warn("Unrecognized commandStr from client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), commandStr);
                sendErrorResponse("Unrecognized commandStr");
            }
        } catch (Exception e) {
            log.error("Error processing message from client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), e.getMessage(), e);
            sendErrorResponse(e.getMessage());
        }
    }

    private void sendErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setState(1);

        if (printWriter != null) {
            printWriter.println(JsonUtil.toJson(errorResponse));
            log.info("发送错误响应给客户端 {}: {}", clientStatus.getIp() + ":" + clientStatus.getPort(), message);
        } else {
            log.error("无法发送错误响应，PrintWriter 为 null");
        }
    }

    private void cleanup() {
        try {
            log.info("清理Socket客户端资源中: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort());
            if (printWriter != null) {
                printWriter.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            clientFactory.removeClient(this.clientStatus.getIp() + ":" + clientStatus.getPort());
            log.info("Socket客户端资源清理完毕: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort());
        } catch (IOException e) {
            log.error("Error closing client socket for client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), e.getMessage(), e);
        }
    }
}


