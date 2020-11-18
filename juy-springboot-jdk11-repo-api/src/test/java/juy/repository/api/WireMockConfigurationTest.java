package juy.repository.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import juy.repository.SampleRepository;
import juy.repository.api.config.AutoConfiguration;
import juy.repository.model.Sample;
import juy.spring.ext.RestTemplateConfiguration;
import juy.spring.ext.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

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
        sample.setName("sample name");
        sample.setValue("sample value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        final Sample result = repository.save(sample);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(sample.getName(), result.getName());
        Assertions.assertEquals(sample.getValue(), result.getValue());
    }

    @Test
    public void shouldReplace() {
        final Sample sample = new Sample();
        sample.setId(1);
        sample.setName("sample name");
        sample.setValue("sample value");
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        Assertions.assertTrue(repository.replace(sample));
    }

    @Test
    public void shouldUpdate() {
        final Sample sample = new Sample();
        sample.setId(1);
        sample.setName("sample name");
        sample.setValue("sample value");
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

    @Test
    public void shouldFindById() {
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        final Sample sample = repository.findById(1);
        Assertions.assertEquals(1, sample.getId());
        Assertions.assertEquals("sample name", sample.getName());
        Assertions.assertEquals("sample value", sample.getValue());
    }

    @Test
    public void shouldList() {
        final SampleRepository repository = context.getBean(ApiSampleRepository.class);
        final List<Sample> samples = repository.list();
        Assertions.assertEquals(1, samples.get(0).getId());
        Assertions.assertEquals("sample name", samples.get(0).getName());
        Assertions.assertEquals("sample value", samples.get(0).getValue());
        Assertions.assertEquals(2, samples.get(1).getId());
        Assertions.assertEquals("sample name 2", samples.get(1).getName());
        Assertions.assertEquals("sample value 2", samples.get(1).getValue());
    }
}

@Configuration
@ImportAutoConfiguration(value = { AutoConfiguration.class, RestTemplateConfiguration.class })
@PropertySource(value = "classpath:repo-api-test.yml", factory = YamlPropertySourceFactory.class)
class WireMockConfigurationTestConfig {

}
