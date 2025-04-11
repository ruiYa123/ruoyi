package com.ruoyi.business.notification.email.controller;

/**
 * @author Zipeng
 * @date 2025年04月11日 9:21
 */

import com.ruoyi.business.notification.email.model.Email;
import com.ruoyi.business.notification.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        Email email = new Email();
        email.setSendTo(to);
        email.setSubject(subject);
        email.setContent(text);
        email.setCreatedBy("妙想");
        emailService.sendEmail(email);
        return "Email sent successfully!";
    }
}
