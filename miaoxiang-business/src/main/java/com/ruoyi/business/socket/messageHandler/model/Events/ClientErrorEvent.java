package com.ruoyi.business.socket.messageHandler.model.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class ClientErrorEvent extends BaseMessage {
    @JsonProperty("ErrorMsg")
    private ErrorMessage errorMsg;

    @Data
    public static class ErrorMessage {
        @JsonProperty("ClientNames")
        private String clientNames;

        @JsonProperty("ProjectName")
        private String projectName;

        @JsonProperty("AssignmentName")
        private String assignmentName;

        @JsonProperty("ErrorText")
        private String errorText;
    }
}
