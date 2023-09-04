package com.modak.te.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailGatewayImpl implements EmailGateway{

    @Override
    public void send(String recipient, String subject, String body) {
        log.info("Sending email: to {} | subject: {} | body: {}",
                recipient,
                subject,
                body
        );
    }
}
