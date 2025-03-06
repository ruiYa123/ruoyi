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
        ObjectMapper objectMapper = new ObjectMapper(); // 用于解析 JSON
        try (InputStream inputStream = clientSocket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 循环读取数据
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                completeMessage.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

                // 检查是否为完整的 JSON
                while (true) {
                    String jsonData = completeMessage.toString();
                    if (isValidJson(jsonData)) {
                        log.info("Received JSON data from client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), jsonData);
                        handleClientMessage(jsonData); // 处理完整的 JSON
                        completeMessage.setLength(0); // 清空已处理的数据
                    } else {
                        // 如果不是完整的 JSON，检查是否可以继续读取更多数据
                        break; // 跳出内层循环，继续读取新的数据
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

    // 检查字符串是否为有效的 JSON
    private boolean isValidJson(String json) {
        try {
            if (json == null || json.isEmpty()) {
                return false;
            }
            new ObjectMapper().readTree(json); // 尝试解析 JSON
            log.info("是完整json");
            return true; // 如果解析成功，返回 true
        } catch (Exception e) {
            return false; // 如果解析失败，返回 false
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
            serviceRegistry.unregister(this.clientStatus.getIp() + ":" + clientStatus.getPort());
            log.info("Socket客户端资源清理完毕: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort());
        } catch (IOException e) {
            log.error("Error closing client socket for client {}: {}", this.clientStatus.getIp() + ":" + clientStatus.getPort(), e.getMessage(), e);
        }
    }
}
