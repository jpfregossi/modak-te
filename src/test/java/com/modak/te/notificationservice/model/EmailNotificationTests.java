package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.exception.CuotaExcededException;
import com.modak.te.notificationservice.service.EmailGateway;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmailNotificationTests {

    @Test
    void shouldCallEmailGatewaySendMethod() {
        EmailGateway gateway = mock(EmailGateway.class);
        List<Rule> rules = new ArrayList<>();
        StatusEmailNotification notification = new StatusEmailNotification(
                gateway,
                "jpfregossi@gamil.com",
                "New notification",
                "Status Notification"
        );

        notification.send();

        verify(gateway, times(1)).send("jpfregossi@gamil.com", "New notification", "Status Notification");
    }
}
