package com.ruoyi.business.socket.messageHandler.model.Events;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class ClientAddEvent extends BaseMessage {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("Port")
    private int port;

    @JsonProperty("State")
    private Long state;
}
