package juy.notification.repository;

import juy.notification.model.Notification;

public interface NotificationRepository {

    boolean send(Notification notification);
}
