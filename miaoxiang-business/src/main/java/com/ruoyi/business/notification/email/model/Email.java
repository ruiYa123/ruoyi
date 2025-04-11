package com.ruoyi.business.notification.email.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zipeng
 * @date 2025年04月10日 15:44
 */

@Data
public class Email implements Serializable {
    /**
     * 收件人
     */
    private String sendTo;
    /**
     * 主题（业务的标识）
     */
    private String subject;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 操作人
     */
    private String createdBy = "深度云训练平台";
}
