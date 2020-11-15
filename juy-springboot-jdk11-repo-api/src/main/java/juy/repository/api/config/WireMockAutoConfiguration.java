package juy.repository.api.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import juy.spring.ext.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;

@Configuration
@EnableConfigurationProperties(WiremockProperties.class)
@PropertySource(value = "classpath:config/repo-api.yml", factory = YamlPropertySourceFactory.class)
public class WireMockAutoConfiguration {

    private WireMockServer server;

    @Autowired
    private WiremockProperties wiremockProperties;

    @Bean
    public WireMockServer wiremock()
    {
        final WireMockConfiguration options = WireMockConfiguration.options().bindAddress(wiremockProperties.getHost())
                .port(wiremockProperties.getPort()).disableRequestJournal();

        server = new WireMockServer(options);
        server.start();
        return server;
    }

    @PreDestroy
    public void cleanup()
    {
        if (server != null && server.isRunning())
        {
            server.stop();
        }
    }
}
