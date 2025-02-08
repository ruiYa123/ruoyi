package com.ruoyi.business.socket.messageHandler.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseRequest;
import lombok.Data;

@Data
public class MCPauseTrainCommand extends BaseRequest {
    @JsonProperty("ClientName")
    private String clientName;

    public MCPauseTrainCommand() {
        this.setCommandStr("MC_PauseTrain");
    }
}

