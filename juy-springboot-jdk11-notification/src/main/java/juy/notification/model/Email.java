package juy.notification.model;

import lombok.Data;

@Data
public class Email {

    private String from;
    private String to;
    private String subject;
    private String body;
    private int expiryInSeconds;
    // TODO
    private String timestamp;

}
