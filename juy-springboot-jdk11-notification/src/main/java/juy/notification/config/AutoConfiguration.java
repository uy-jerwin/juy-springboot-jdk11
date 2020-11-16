package juy.notification.config;

import juy.spring.ext.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnProperty(name = "notification.enabled", havingValue = "true", matchIfMissing = false)
@Configuration
@EnableConfigurationProperties(NotificationProperties.class)
@ComponentScan("juy.notification")
@PropertySource(value = { "classpath:config/notification.yml"}, factory = YamlPropertySourceFactory.class)
public class AutoConfiguration {

}
