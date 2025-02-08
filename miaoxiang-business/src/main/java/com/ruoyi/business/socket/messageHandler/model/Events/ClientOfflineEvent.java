package com.ruoyi.business.socket.messageHandler.model.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class ClientOfflineEvent extends BaseMessage {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Reason")
    private String reason;

}

