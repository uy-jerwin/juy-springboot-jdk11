package juy.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {

    private Auth auth;
    private Server server;

    @Data
    public static class Auth {

        private String url;
        private String grantType;
        private String clientId;
        private String clientSecret;

    }

    @Data
    public static class Server {
        private String url;
    }
}



