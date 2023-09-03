package com.modak.te.notificationservice.repository;

import com.modak.te.notificationservice.model.FrequencyRuleEntity;

import java.util.List;

public interface RulesRepository {
    List<FrequencyRuleEntity> getByMessageType(String messageType);
}
