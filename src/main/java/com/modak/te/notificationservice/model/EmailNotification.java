package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.service.EmailGateway;

import java.util.List;

public abstract class EmailNotification implements Notification{
    protected EmailGateway gateway;
    protected String recipient;
    protected String body;
    protected String subject;

    public EmailNotification(EmailGateway gateway, String recipient, String body, String subject) {
        this.gateway = gateway;
        this.recipient = recipient;
        this.body = body;
        this.subject = subject;
    }

    @Override
    public abstract void send();
}
