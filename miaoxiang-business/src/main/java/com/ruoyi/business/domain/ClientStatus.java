package com.ruoyi.business.domain;

import lombok.Data;

@Data
public class ClientStatus {

    private String name;
    private String ip;
    private int port;
    private Double cpu = 0.0;
    private Double gpu = 0.0;
    private Double cpuMem = 0.0;
    private Double gpuMem = 0.0;
    private Double disk = 0.0;
    private Long assignmentId;
    private String assignmentName;
    private Long projectId;
    private String projectName;
    private Double trainProcess;
    private Integer state;

    public ClientStatus(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ClientStatus() {

    }

}
