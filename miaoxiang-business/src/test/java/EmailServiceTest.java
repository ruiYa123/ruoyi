import com.ruoyi.business.notification.email.model.Email;
import com.ruoyi.business.notification.email.service.EmailService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Zipeng
 * @date 2025年04月11日 9:05
 */

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        Email email = new Email();
        email.setSubject("Subject");
        email.setContent("Content");
        email.setSendTo("guozipengRuiya@163.com");
        email.setCreatedBy("妙想");
        emailService.sendEmail(email);
    }

}
