package com.ruoyi.business.socket.Client;

import com.ruoyi.business.socket.SocketService;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.response.ErrorResponse;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String ip;
    private final int port;
    private final String clientKey;
    private final Map<String, BaseMessageHandler> messageHandlerMap = new HashMap<>();

    public ClientHandler(Socket socket, List<BaseMessageHandler> messageHandlers) {
        this.clientSocket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
        this.clientKey = this.ip + ":" + this.port;
        for (BaseMessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getCommand(), handler);
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                log.info("Received JSON data from client {}: {}", clientKey, inputLine);
                handleClientMessage(inputLine);
            }
        } catch (IOException e) {
            if ("Socket closed".equals(e.getMessage())) {
                log.info("Socket客户端已关闭: {}", clientKey);
            } else {
                log.warn("IOException occurred while reading input for client {}: {}", clientKey, e.getMessage());
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
                handler.handle(jsonData, ip, port);
            } else {
                log.warn("Unrecognized commandStr from client {}: {}", clientKey, commandStr);
                sendErrorResponse("Unrecognized commandStr");
            }
        } catch (Exception e) {
            log.error("Error processing message from client {}: {}", clientKey, e.getMessage(), e);
            sendErrorResponse(e.getMessage());
        }
    }

    private void sendErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setState(1);
        SocketService.sendMessageToClientByAddress(this.ip, this.port, JsonUtil.toJson(errorResponse));
    }

    private void cleanup() {
        try {
            log.info("清理Socket客户端资源中: {}", clientKey);
            clientSocket.close();
            SocketService.removeClient(clientKey);
            log.info("Socket客户端资源清理完毕: {}", clientKey);
        } catch (IOException e) {
            log.error("Error closing client socket for client {}: {}", clientKey, e.getMessage());
        }
    }
}

