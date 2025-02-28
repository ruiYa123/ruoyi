package com.ruoyi.business.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class FileListRequest {
    private List<String> paths;
}
