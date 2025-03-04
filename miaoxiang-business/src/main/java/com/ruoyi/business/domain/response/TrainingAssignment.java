package com.ruoyi.business.domain.response;

import com.ruoyi.business.domain.Assignment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainingAssignment extends Assignment {
    private BigDecimal progress;
}
