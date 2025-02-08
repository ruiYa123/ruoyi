package com.ruoyi.business.socket.messageHandler.model.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class ClientProjectTrainStartEvent extends BaseMessage {
    @JsonProperty("ClientNames")
    private String clientNames;

    @JsonProperty("TrainPara")
    private TrainParameter trainPara;

    @Data
    public static class TrainParameter {
        @JsonProperty("ProjectName")
        private String projectName;

        @JsonProperty("Train_Start")
        private int trainStart; // 1: 开始
    }
}
