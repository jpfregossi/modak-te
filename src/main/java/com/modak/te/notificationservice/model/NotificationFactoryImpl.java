package com.modak.te.notificationservice.model;

import org.springframework.stereotype.Service;

@Service
public class NotificationFactoryImpl implements NotificationFactory{


    @Override
    public Notification buildNotification(String notificationType, String message, String userId) {
        return null;
    }
}
