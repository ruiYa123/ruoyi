package com.ruoyi.business.domain.request;

import com.ruoyi.business.domain.Client;
import lombok.Data;

@Data
public class MessageRequest extends Client {
    private String jsonMessage;
}
