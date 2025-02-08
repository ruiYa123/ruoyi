package com.ruoyi.business.socket.messageHandler.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseRequest;
import lombok.Data;

@Data
public class MCStopTrainCommand extends BaseRequest {
    @JsonProperty("ClientName")
    private String clientName;

    public MCStopTrainCommand() {
        this.setCommandStr("MC_StopTrain");
    }
}

