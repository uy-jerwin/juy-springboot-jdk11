package juy.notification.model;

import lombok.Data;

import java.util.Map;

@Data
public class SMS {

    private String recipients;
    private String body;

    private int expiryInSeconds;
    private String templateCode;
    private Map<String, String> templateTokens;
    // TODO
    private String timestamp;

}
