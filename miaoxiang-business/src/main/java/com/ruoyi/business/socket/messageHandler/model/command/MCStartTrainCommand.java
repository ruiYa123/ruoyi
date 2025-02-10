package com.ruoyi.business.socket.messageHandler.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.business.socket.messageHandler.model.BaseRequest;
import lombok.Data;

import static com.ruoyi.business.socket.messageHandler.handler.CommandEnum.MC_START_TRAIN;

@Data
public class MCStartTrainCommand extends BaseRequest {
    @JsonProperty("ClientName")
    private String clientName;

    @JsonProperty("ProjectName")
    private String projectName;

    @JsonProperty("TrainPara")
    private TrainParameters trainPara;

    @Data
    public static class TrainParameters {
        @JsonProperty("PreTrain_Model")
        private String preTrainModel; // "N", "S", "M", "L", "X"

        @JsonProperty("Epoch")
        private int epoch; // 30~300

        @JsonProperty("BatchSize")
        private int batchSize; // 16

        @JsonProperty("ImgSize")
        private int imgSize; // 640
    }

    public MCStartTrainCommand() {
        this.setCommandStr(MC_START_TRAIN.getCommandStr());
    }
}


