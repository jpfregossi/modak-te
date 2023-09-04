package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import com.modak.te.notificationservice.model.RuleFactoryImpl;
import com.modak.te.notificationservice.repository.FrequencyRulesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngineImpl implements RuleEngine{
    private final FrequencyRulesRepository repository;
    private final RuleFactoryImpl mapper;

    public RuleEngineImpl(FrequencyRulesRepository repository, RuleFactoryImpl mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void validateRules(String messageType, String userId) {
        List<FrequencyRuleEntity> rules = repository.getByMessageType(messageType);

        rules.stream()
                .map(entity -> mapper.from(entity))
                .map(r -> r.validate(userId))
                .toList();
    }
}
