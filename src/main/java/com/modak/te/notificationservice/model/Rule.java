package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.service.RulesEnforcer;

public abstract class Rule {
    protected RulesEnforcer enforcer;

    public Rule(RulesEnforcer enforcer) {
        this.enforcer = enforcer;
    }

    public abstract Boolean validate(String userId);
}
