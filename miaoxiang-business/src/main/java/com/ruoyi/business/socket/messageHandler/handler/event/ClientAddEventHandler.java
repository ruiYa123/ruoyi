package com.ruoyi.business.socket.messageHandler.handler.event;

import com.ruoyi.business.domain.Client;
import com.ruoyi.business.service.IClientService;
import com.ruoyi.business.service.impl.ClientServiceImpl;
import com.ruoyi.business.socket.messageHandler.handler.BaseMessageHandler;
import com.ruoyi.business.socket.messageHandler.model.Events.ClientAddEvent;
import com.ruoyi.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientAddEventHandler implements BaseMessageHandler {

    @Autowired
    private IClientService clientService;

    @Override
    public void handle(String jsonMessage) {
        ClientAddEvent message = JsonUtil.fromJson(jsonMessage, ClientAddEvent.class);
        Client client = new Client();
        BeanUtils.copyProperties(message, client);
        clientService.insertClient(client);
    }

    @Override
    public String getCommand() {
        return "Client_Add";
    }
}
