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

    public void run() {
        while (true) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    log.info("Received JSON data from client: {}", inputLine);
                    handleClientMessage(inputLine);
                }
                log.info("Client has closed the connection: {}", clientKey);
                break;
            } catch (IOException e) {
                log.warn("IOException occurred while reading input for client {}: {}", clientKey, e.getMessage());
            }
        }

        try {
            log.info("客户端连接断开");
            clientSocket.close();
            SocketService.removeClient(clientKey);
            log.info("Client socket closed and resources cleaned up for client: {}", clientKey);
        } catch (IOException e) {
            log.error("Error closing client socket for client {}: {}", clientKey, e.getMessage());
        }
    }

    private void handleClientMessage(String jsonData) {
        try {
            String commandStr = JsonUtil.getField(jsonData, "CommandStr").toString();
            BaseMessageHandler handler = messageHandlerMap.get(commandStr);
            if (handler != null) {
                handler.handle(jsonData);
            } else {
                log.info("未知的命令: {}", commandStr);
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Unrecognized commandStr");
                errorResponse.setState(1);
                SocketService.sendMessageToClientByAddress(this.ip, this.port, JsonUtil.toJson(errorResponse));
            }
        } catch (Exception e) {
            log.info("消息处理错误：:", e);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            errorResponse.setState(1);
            SocketService.sendMessageToClientByAddress(this.ip, this.port, JsonUtil.toJson(errorResponse));
        }
    }

}
