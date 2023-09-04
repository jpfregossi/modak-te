package com.modak.te.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailGatewayImpl implements EmailGateway{
    private final JavaMailSender sender;

    public EmailGatewayImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    // TODO: error management
    @Override
    public void send(String recipient, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(body);
            sender.send(message);
        } catch(Exception e) {
            log.error("No valid email account has been set yet.");
        }

        log.info("Sending email: to {} | subject: {} | body: {}",
                recipient,
                subject,
                body
        );
    }
}
