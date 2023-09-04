package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.model.Notification;

public interface NotificationService {

    void process(String notificationType, Notification request);
}
