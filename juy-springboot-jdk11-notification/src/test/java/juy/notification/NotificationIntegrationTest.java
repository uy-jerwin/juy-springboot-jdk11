package juy.notification;

import juy.notification.config.AutoConfiguration;
import juy.notification.model.Email;
import juy.notification.model.Notification;
import juy.notification.repository.NotificationRepository;
import juy.spring.ext.RestTemplateConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.UUID;

public class NotificationIntegrationTest {

    public static void main(String[] args)
    {
        System.setProperty("notification.enabled", "true");
        // REPLACE WITH SECRET
        //System.setProperty("jasypt.encryptor.password", "");
        
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(NotificationIntegrationTestConfig.class);
        final NotificationRepository repository = context.getBean(NotificationRepository.class);
        final Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setOriginator("TEST");

        final Email email = new Email();
        email.setFrom("Metrobank Mobile Banking <metrobankdirect@metrobank.com.ph");
        email.setTo("uy.jerwin@gmail.com");
        email.setBody("TEST MESSAGE");

        final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(DateTimeFormatter.ISO_LOCAL_TIME)
                .toFormatter();
        email.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        notification.setEmail(email);

        repository.send(notification);

        context.close();
    }
}

@Configuration
@EnableAutoConfiguration
@Import({AutoConfiguration.class, RestTemplateConfiguration.class})
class NotificationIntegrationTestConfig {

}