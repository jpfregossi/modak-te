package com.modak.te.notificationservice.service;

public interface RuleEngine {
    void validateRules(String messageType, String userId);
}
