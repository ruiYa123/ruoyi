package com.ruoyi.business.socket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.business.domain.ClientStatus;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientAddEvent;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.business.socket.service.ServiceRegistry;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.response.ErrorResponse;
import com.ruoyi.common.utils.JsonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.CLIENT_ADD;
import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.GET_CLIENT_STATE;

@Slf4j
public class ClientHandler extends Thread {
    private final Socket clientSocket;
    @Getter
    private final PrintWriter printWriter;
    private final Map<String, BaseMessageHandler> messageHandlerMap = new HashMap<>();
    @Getter
    private final ClientStatus clientStatus;
    private final ServiceRegistry serviceRegistry;

    public ClientHandler(Socket socket, List<BaseMessageHandler> messageHandlers, ServiceRegistry serviceRegistry) throws IOException {
        this.clientSocket = socket;
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.clientStatus = new ClientStatus(socket.getInetAddress().getHostAddress(), socket.getPort());
        this.serviceRegistry = serviceRegistry;
        for (BaseMessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getCommand(), handler);
        }
    }

    @Override
    public void run() {
        StringBuilder completeMessage = new StringBuilder();
        try (InputStream inputStream = clientSocket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                completeMessage.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

                while (true) {
                    String jsonData = completeMessage.toString();
                    if (JsonUtil.isValidJson(jsonData)) {
                        log.info("Received JSON data from client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), jsonData);
                        handleClientMessage(jsonData);
                        completeMessage.setLength(0);
                    } else {
                        break;
                    }
                }
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
            if (Objects.equals(commandStr, CLIENT_ADD.getCommandStr())) {
                ClientAddEvent clientAddEvent = JsonUtil.fromJson(jsonData, ClientAddEvent.class);
                serviceRegistry.register(clientAddEvent.getName(), this.printWriter);
                this.clientStatus.setName(clientAddEvent.getName());
            }
            if (Objects.equals(commandStr, GET_CLIENT_STATE.getCommandStr())) {
                MCGetClientStateFeedBack mcGetClientStateFeedBack = JsonUtil.fromJson(jsonData, MCGetClientStateFeedBack.class);
                serviceRegistry.register(mcGetClientStateFeedBack.getClientState().getName(), this.printWriter);
                this.clientStatus.setName(mcGetClientStateFeedBack.getClientState().getName());
            }
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
            serviceRegistry.unregister(this.clientStatus.getName());
            log.info("Socket客户端资源清理完毕: {}", this.clientStatus.getName());
        } catch (IOException e) {
            log.error("Error closing client socket for client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), e.getMessage(), e);
        }
    }
}
