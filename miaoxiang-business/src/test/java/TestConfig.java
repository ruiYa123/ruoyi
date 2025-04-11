import com.ruoyi.business.notification.email.service.EmailService;
import com.ruoyi.business.notification.email.service.impl.EmailServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * @author Zipeng
 * @date 2025年04月11日 9:16
 */

public class TestConfig {
    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }

}
