package com.ruoyi.business.socket.messageHandler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseRequest {
    @JsonProperty("CommandStr")
    private String commandStr;
}
