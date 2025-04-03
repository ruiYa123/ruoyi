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
        private Double trainPercentage;

        @JsonProperty("Train_Process")
        private String trainProcess;

        @JsonProperty("Loss_Curve")
        private Double lossCurve;

        @JsonProperty("Pre_Curve")
        private Double preCurve;

        @JsonProperty("ProjectName")
        private String projectName;

        @JsonProperty("AssignmentName")
        private String assignmentName;
    }
}
