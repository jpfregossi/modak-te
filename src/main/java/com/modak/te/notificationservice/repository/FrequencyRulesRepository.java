package com.modak.te.notificationservice.repository;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;

import java.util.List;

public interface RulesRepository {
    List<FrequencyRuleEntity> getByMessageType(String messageType);
}
