package com.ruoyi.business.socket.messageHandler.model.feedBack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseMessage;
import lombok.Data;

@Data
public class MCGetClientStateFeedBack extends BaseMessage {
    @JsonProperty("ClientState")
    private ClientState clientState;

    @Data
    public static class ClientState {
        @JsonProperty("Name")
        private String name;

        @JsonProperty("State")
        private int state; // 0: 空闲, 1: 训练中

        @JsonProperty("TrainState")
        private TrainState trainState;

        @Data
        public static class TrainState {
            @JsonProperty("Train_Process")
            private String trainProcess; // "Collect_Label", "Datasets_Process", "Add_Datasets", "Train_Model", "Transform_Model"
        }
    }
}
