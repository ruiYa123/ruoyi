package com.ruoyi.business.notification.email.service.impl;

import com.ruoyi.business.notification.email.model.Email;
import com.ruoyi.business.notification.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * @author Zipeng
 * @date 2025年04月10日 15:47
 */

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("miaoxiangtech@163.com");
        message.setTo(email.getSendTo());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        javaMailSender.send(message);
    }

}
