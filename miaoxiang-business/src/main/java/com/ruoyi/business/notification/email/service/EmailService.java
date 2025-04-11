package com.ruoyi.business.notification.email.service;

import com.ruoyi.business.notification.email.model.Email;
import  org.springframework.stereotype.Service;

/**
 * @author Zipeng
 * @date 2025年04月10日 15:46
 */


@Service
public interface EmailService  {
    public void sendEmail(Email email);
}

