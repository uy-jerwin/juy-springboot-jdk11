package juy.repository.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import juy.repository.SampleRepository;
import juy.repository.api.config.AutoConfiguration;
import juy.repository.api.config.RestTemplateConfiguration;
import juy.repository.model.Sample;
import juy.spring.ext.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class WireMockConfigurationTest {

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void init() {
        context = new AnnotationConfigApplicationContext(WireMockConfigurationTestConfig.class);
        Assertions.assertNotNull(context.getBean(WireMockServer.class));

        final ApiSampleRepository repository = context.getBean(ApiSampleRepository.class);
        Assertions.assertNotNull(repository);
    }

    @Test
    public void shouldSave() {
        final Sample sample = new Sample();
        sample.setName("test");
        sample.setValue("test value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        final Sample result = repository.save(sample);
        Assertions.assertEquals(sample.getName(), result.getName());
        Assertions.assertEquals(sample.getValue(), result.getValue());
    }

    @Test
    public void shouldReplace() {
        final Sample sample = new Sample();
        sample.setId(1);
        sample.setName("test");
        sample.setValue("test value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        Assertions.assertTrue(repository.replace(sample));
    }

    @Test
    public void shouldUpdate() {
        final Sample sample = new Sample();
        sample.setId(1);
        sample.setName("test");
        sample.setValue("test value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        Assertions.assertTrue(repository.update(sample));
    }

    @Test
    public void shouldDelete() {
        final Sample sample = new Sample();
        sample.setId(1);
        sample.setName("test");
        sample.setValue("test value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        Assertions.assertTrue(repository.delete(sample.getId()));
    }
}

@Configuration
@ImportAutoConfiguration(value = { AutoConfiguration.class, RestTemplateConfiguration.class })
@PropertySource(value = "classpath:repo-api-test.yml", factory = YamlPropertySourceFactory.class)
class WireMockConfigurationTestConfig {

}
