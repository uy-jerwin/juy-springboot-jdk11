package juy.repository.db;

import juy.repository.db.config.AutoConfiguration;
import juy.repository.db.config.DataSourceProperties;
import juy.spring.ext.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class H2ConfigurationTest {

    @Test
    public void init()
    {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(H2ConfigurationTestConfig.class);
        Assertions.assertNotNull(context.getBean(DataSourceProperties.class));
        Assertions.assertNotNull(context.getBean(LocalContainerEntityManagerFactoryBean.class));
        Assertions.assertNotNull(context.getBean(PlatformTransactionManager.class));
        context.close();
    }
}

@Configuration
@ImportAutoConfiguration(AutoConfiguration.class)
@PropertySource(value = "classpath:repo-db-test.yml", factory = YamlPropertySourceFactory.class)
class H2ConfigurationTestConfig {

}