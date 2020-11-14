package juy.repository.db.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "repo.db.datasource")
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
