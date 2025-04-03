package com.ruoyi.business.domain.response;

import com.ruoyi.business.domain.AssignmentTrain;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TrainDetail {
    private Double progress;
    private String trainProcess;
    private Map<String, List<Double>> jsonData;
}
