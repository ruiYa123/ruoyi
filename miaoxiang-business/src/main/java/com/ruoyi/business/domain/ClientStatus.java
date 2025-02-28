package com.ruoyi.business.domain;

import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import lombok.Data;

@Data
public class ClientStatus {

    private String name;
    private String ip;
    private int port;
    private Long assignmentId;
    private String assignmentName;
    private Long projectId;
    private String projectName;
    private Double trainPercentage;
    private Assignment assignment;

    private MCGetClientStateFeedBack mcGetClientStateFeedBack = new MCGetClientStateFeedBack();

    private MCGetTrainStateFeedBack mcGetTrainStateFeedBack = new MCGetTrainStateFeedBack();

    public ClientStatus(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ClientStatus() {

    }

}
