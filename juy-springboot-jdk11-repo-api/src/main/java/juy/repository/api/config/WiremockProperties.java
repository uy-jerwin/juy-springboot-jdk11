package juy.repository.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "repo.api")
public class WiremockProperties {

    private String host;
    private int port;

}
