package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;

public interface RuleFactory {
    Rule from(FrequencyRuleEntity entity);
}
