package com.ruoyi.business.socket.messageHandler.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    @JsonProperty("CommandStr")
    private String commandStr = "error";
    @JsonProperty("Message")
    private String message;
    @JsonProperty("State")
    private int state;
}
