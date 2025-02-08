package com.ruoyi.business.socket.messageHandler.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseRequest;
import lombok.Data;

@Data
public class MCGetClientStateCommand extends BaseRequest {
    @JsonProperty("ClientNames")
    private String clientNames;

    public MCGetClientStateCommand() {
        this.setCommandStr("MC_GetClientState");
    }
}
