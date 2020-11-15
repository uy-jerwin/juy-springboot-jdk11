package juy.enc;

import juy.enc.config.AutoConfiguration;
import juy.spring.ext.YamlPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
public class EncryptionDecryptionIntegrationTest {

    @Test
    public void shouldEncryptDecrypt()
    {
        System.setProperty("jasypt.encryptor.password", "12345");

        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EncryptionDecryptionIntegrationTestConfig.class);
        final StringEncryptor encryptor = context.getBean(StringEncryptor.class);
        Assertions.assertNotNull(encryptor);

        final String value = "sample-password";
        final String encrypted = encryptor.encrypt(value);
        log.info(encrypted);
        Assertions.assertEquals(value, encryptor.decrypt(encrypted));

        final Environment env = context.getEnvironment();

        Assertions.assertEquals(value, encryptor.decrypt(env.getProperty("enc.sample.password-raw")));
        Assertions.assertEquals(value, env.getProperty("enc.sample.password-enc"));

        context.close();
    }

}

@Configuration
@EnableAutoConfiguration
@Import(AutoConfiguration.class)
@PropertySource(value = "classpath:test-enc.yml", factory = YamlPropertySourceFactory.class)
class EncryptionDecryptionIntegrationTestConfig {

}