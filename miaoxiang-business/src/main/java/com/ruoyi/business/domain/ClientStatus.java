package com.ruoyi.business.domain;

import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetClientStateFeedBack;
import com.ruoyi.business.socket.messageHandler.model.feedBack.MCGetTrainStateFeedBack;
import lombok.Data;

@Data
public class ClientStatus {

    private Client client = new Client();
    private Long assignmentId;
    private String assignmentName;
    private Long projectId;
    private String projectName;
    private Double trainPercentage;
    private Assignment assignment;

    private MCGetClientStateFeedBack mcGetClientStateFeedBack = new MCGetClientStateFeedBack();

    private MCGetTrainStateFeedBack mcGetTrainStateFeedBack = new MCGetTrainStateFeedBack();

    public ClientStatus(String ip, int port) {
        this.client.setIp(ip);
        this.client.setPort(port);
    }

    public ClientStatus() {

    }

}
