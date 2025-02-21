package com.ruoyi.business.socket.messageHandler.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseRequest;
import lombok.Data;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.GET_TRAIN_STATE;

@Data
public class MCGetTrainStateCommand extends BaseRequest {
    @JsonProperty("ClientName")
    private String clientName;

    public MCGetTrainStateCommand() {
        this.setCommandStr(GET_TRAIN_STATE.getCommandStr());
    }
}
