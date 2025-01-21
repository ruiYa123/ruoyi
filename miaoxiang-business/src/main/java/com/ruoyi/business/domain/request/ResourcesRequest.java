package com.ruoyi.business.domain.request;
import com.ruoyi.business.domain.Resources;
import lombok.Data;

@Data
public class ResourcesRequest extends Resources {
    private String assignmentName;
    private String projectName;
}
