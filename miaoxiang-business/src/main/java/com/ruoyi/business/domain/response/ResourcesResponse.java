package com.ruoyi.business.domain.response;

import lombok.Data;

import java.util.Date;

@Data
public class ResourcesResponse {
    private String path;
    private String jsonPath;
    private String name;
    private String createTime;
    private Integer unMarkedCount;

    public ResourcesResponse(String path, String name, String createTime ,boolean hasJson) {
        this.path = path;
        this.name = name;
        this.createTime = createTime;
        if(hasJson) {
            this.jsonPath = this.path.replace(".bmp", ".json");
        }
    }
}
