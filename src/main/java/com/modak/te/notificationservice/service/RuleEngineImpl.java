package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.model.FrequencyRuleEntity;
import com.modak.te.notificationservice.model.RuleMapper;
import com.modak.te.notificationservice.repository.RulesRepository;

import java.util.List;

public class RuleEngineImpl implements RuleEngine{
    private final RulesRepository repository;
    private final RulesEnforcer rulesEnforcer;
    private final RuleMapper mapper;

    public RuleEngineImpl(RulesRepository repository, RulesEnforcer rulesEnforcer, RuleMapper mapper) {
        this.repository = repository;
        this.rulesEnforcer = rulesEnforcer;
        this.mapper = mapper;
    }

    @Override
    public void validateRules(String messageType, String userId) {
        List<FrequencyRuleEntity> rules = repository.getByMessageType(messageType);

        rules.stream()
                .map(m -> { System.out.println(m.getMessageType()); return m;})
                .map(entity -> mapper.from(rulesEnforcer, entity))
                .map(r -> r.validate(userId))
                .toList();
    }
}
