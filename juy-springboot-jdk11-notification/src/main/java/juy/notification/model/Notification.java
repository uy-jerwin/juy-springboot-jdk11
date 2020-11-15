package juy.notification.model;

import lombok.Data;

@Data
public class Notification {

    private String id;
    private String originator;
    private SMS sms;
    private Email email;

}
