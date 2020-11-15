import juy.spring.ext.YamlPropertySourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class YamlPropertySourceFactoryTest {

    @Test
    public void shouldReadProperties() throws IOException {
        final YamlPropertySourceFactory factory = new YamlPropertySourceFactory();

        final PropertySource source = factory.createPropertySource("test", new EncodedResource(new ClassPathResource("test.yaml")));
        Assertions.assertEquals("chuck norris", source.getProperty("test.config.message"));
    }
}
