package juy.repository.db;

import juy.repository.db.config.AutoConfiguration;
import juy.repository.db.config.DataSourceProperties;
import juy.repository.db.repository.H2SampleRepository;
import juy.repository.model.Sample;
import juy.spring.ext.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

public class H2ConfigurationTest {

    @Test
    public void init()
    {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(H2ConfigurationTestConfig.class);
        Assertions.assertNotNull(context.getBean(DataSourceProperties.class));
        Assertions.assertNotNull(context.getBean(LocalContainerEntityManagerFactoryBean.class));
        Assertions.assertNotNull(context.getBean(PlatformTransactionManager.class));

        final H2SampleRepository repository = context.getBean(H2SampleRepository.class);
        Assertions.assertNotNull(repository);

        final Sample sample = new Sample();
        sample.setName(UUID.randomUUID().toString());
        sample.setValue(UUID.randomUUID().toString());
        final Sample result = repository.save(sample);

        final Sample sampleFromDB = repository.findById(result.getId());
        Assertions.assertNotNull(sampleFromDB);

        sampleFromDB.setName(UUID.randomUUID().toString());
        sampleFromDB.setValue(UUID.randomUUID().toString());
        Assertions.assertTrue(repository.update(sampleFromDB));
        final Sample sampleFromDBUpdated = repository.findById(sampleFromDB.getId());
        Assertions.assertEquals(sampleFromDB.getName(), sampleFromDBUpdated.getName());
        Assertions.assertEquals(sampleFromDB.getValue(), sampleFromDBUpdated.getValue());

        Assertions.assertEquals(1, repository.list().size());
        repository.delete(sampleFromDBUpdated.getId());
        Assertions.assertEquals(0, repository.list().size());

        context.close();
    }
}

@Configuration
@ImportAutoConfiguration(value = {
        AutoConfiguration.class, LiquibaseAutoConfiguration.class
} )
@PropertySource(value = "classpath:repo-db-test.yml", factory = YamlPropertySourceFactory.class)
class H2ConfigurationTestConfig {

}