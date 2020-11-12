package juy.repository.mock.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "repo.mock", havingValue = "true", matchIfMissing = false)
@Configuration
@ComponentScan("juy.repository.mock")
public class AutoConfiguration {

}
