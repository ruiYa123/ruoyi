package com.ruoyi.business.socket.messageHandler.model.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class ClientProjectTrainEndEvent extends BaseMessage {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("ProjectName")
    private String projectName;

    @JsonProperty("AssignmentName")
    private String assignmentName;

    @JsonProperty("TrainPara")
    private TrainParameter trainPara;

    @Data
    public static class TrainParameter {
        @JsonProperty("Train_Complete")
        private Integer trainComplete; // 1: 完成
    }
}
