package com.modak.te.notificationservice.service;

public interface EmailGateway {
    void send(String recipient, String subject, String body);
}
