package com.modak.te.notificationservice.service;

public interface RulesEnforcer {
    public Boolean validate(String key, int condition, long ttl);
}
