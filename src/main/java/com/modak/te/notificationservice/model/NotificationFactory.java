package com.modak.te.notificationservice.model;

public interface NotificationFactory {
    Notification buildNotification(String notificationType, String message, String userId);
}
