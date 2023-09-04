package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.controller.dto.EmailNotificationDTO;
import com.modak.te.notificationservice.service.EmailGateway;

public class EmailNotification implements Notification{
    protected EmailGateway gateway;
    protected String recipient;
    protected String body;
    protected String subject;

    public EmailNotification(EmailGateway gateway, EmailNotificationDTO notification) {
        this.gateway = gateway;
        this.recipient = notification.getEmail();
        this.body = notification.getMessage();
        this.subject = notification.getSubject();
    }

    @Override
    public void send(){
        gateway.send(recipient, subject, body);
    };

    @Override
    public String getUser(){
        return this.recipient;
    };
}
