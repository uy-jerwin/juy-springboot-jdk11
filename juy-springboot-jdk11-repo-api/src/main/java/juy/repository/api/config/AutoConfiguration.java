package juy.repository.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "repo.api", havingValue = "true", matchIfMissing = false)
@Configuration
@ComponentScan("juy.repository.api")
public class AutoConfiguration {
}
