package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.controller.dto.EmailNotificationDTO;
import com.modak.te.notificationservice.service.EmailGateway;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {
    private final EmailGateway emailGateway;

    public NotificationMapper(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public Notification from(EmailNotificationDTO dto) {
        return new EmailNotification(emailGateway, dto);
    }
}
