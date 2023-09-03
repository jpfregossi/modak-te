package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.service.EmailGateway;

import java.util.List;

public class StatusEmailNotification extends EmailNotification{
    private String template;

    public StatusEmailNotification(EmailGateway gateway, String recipient, String body, String subject) {
        super(gateway, recipient, body, subject);
        template = "Status Update\n";
    }

    @Override
    public void send() {
        gateway.send(recipient, body, subject);
    }
}
