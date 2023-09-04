package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final RuleEngine ruleEngine;

    public NotificationServiceImpl(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @Override
    public void process(String type, Notification notification) {
        ruleEngine.validateRules(type, notification.getUser());
        notification.send();
    }
}
