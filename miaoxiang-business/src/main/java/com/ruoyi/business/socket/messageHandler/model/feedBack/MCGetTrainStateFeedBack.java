package com.ruoyi.business.socket.messageHandler.model.feedBack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class MCGetTrainStateFeedBack extends BaseMessage {
    @JsonProperty("TrainState")
    private TrainState trainState;

    @Data
    public static class TrainState {
        @JsonProperty("Train_Percentage")
        private Double trainPercentage; // 0~100
    }
}
