package juy.repository.db.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "repo.db", havingValue = "true", matchIfMissing = false)
@Configuration
@ComponentScan("juy.repository.db")
public class AutoConfiguration {

}

