package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.controller.dto.EmailNotificationDTO;
import com.modak.te.notificationservice.service.EmailGateway;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class EmailNotificationTests {

    @Test
    void shouldCallEmailGatewaySendMethod() {
        EmailGateway gateway = mock(EmailGateway.class);
        EmailNotificationDTO dto = new EmailNotificationDTO();
        dto.setEmail("jpfregossi@gamil.com");
        dto.setSubject("New notification");
        dto.setMessage("Status Notification");
        Notification notification = new EmailNotification(gateway, dto);

        notification.send();

        verify(gateway, times(1)).send("jpfregossi@gamil.com", "New notification", "Status Notification");
    }
}
