package juy.repository.db.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "repo.db", havingValue = "true", matchIfMissing = false)
@Configuration
@ComponentScan("juy.repository.db")
@ImportAutoConfiguration(classes = LiquibaseAutoConfiguration.class, exclude = DataSourceAutoConfiguration.class)
public class AutoConfiguration {

}

