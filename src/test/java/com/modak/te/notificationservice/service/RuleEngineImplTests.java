package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.exception.CuotaExcededException;
import com.modak.te.notificationservice.model.FrequencyRuleEntity;
import com.modak.te.notificationservice.model.Rule;
import com.modak.te.notificationservice.model.RuleMapper;
import com.modak.te.notificationservice.model.StatusEmailNotification;
import com.modak.te.notificationservice.repository.RulesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class RuleEngineImplTests {
    @Test
    void shouldValidateEveryRule() {
        RulesRepository repo = mock(RulesRepository.class);
        RulesEnforcer enforcer = mock(RulesEnforcer.class);
        FrequencyRuleEntity ruleDTO = new FrequencyRuleEntity();
        ruleDTO.setMessageType("status");
        ruleDTO.setPeriod(1000);
        ruleDTO.setMax(1);
        ruleDTO.setGranularity("RECIPIENT");
        List<FrequencyRuleEntity> entities = new ArrayList<>();
        entities.add(ruleDTO);
        entities.add(ruleDTO);
        entities.add(ruleDTO);
        Rule rule = mock(Rule.class);
        RuleMapper mapper = mock(RuleMapper.class);

        RuleEngineImpl ruleEngine = new RuleEngineImpl(repo, enforcer, mapper);

        when(repo.getByMessageType(anyString())).thenReturn(entities);
        when(mapper.from(enforcer,ruleDTO)).thenReturn(rule);

        ruleEngine.validateRules("status", "userID");

        verify(rule, times(3)).validate("userID");
    }

    @Test
    void shouldThrowExceptionWhenTheLastRuleDoesntValidate() {
        RulesRepository repo = mock(RulesRepository.class);
        RulesEnforcer enforcer = mock(RulesEnforcer.class);
        FrequencyRuleEntity ruleDTO = new FrequencyRuleEntity();
        ruleDTO.setMessageType("status");
        ruleDTO.setPeriod(1000);
        ruleDTO.setMax(1);
        ruleDTO.setGranularity("RECIPIENT");
        FrequencyRuleEntity ruleDTO2 = new FrequencyRuleEntity();
        ruleDTO2.setMessageType("status");
        ruleDTO2.setPeriod(1000);
        ruleDTO2.setMax(1);
        ruleDTO2.setGranularity("RECIPIENT");
        List<FrequencyRuleEntity> entities = new ArrayList<>();
        entities.add(ruleDTO);
        entities.add(ruleDTO);
        entities.add(ruleDTO2);
        Rule rule = mock(Rule.class);
        Rule rule2 = mock(Rule.class);
        RuleMapper mapper = mock(RuleMapper.class);

        RuleEngineImpl ruleEngine = new RuleEngineImpl(repo, enforcer, mapper);

        when(repo.getByMessageType(anyString())).thenReturn(entities);
        when(mapper.from(enforcer,ruleDTO)).thenReturn(rule);
        when(mapper.from(enforcer,ruleDTO2)).thenReturn(rule2);

        when(rule.validate("userID")).thenReturn(true);
        when(rule2.validate("userID")).thenThrow(new CuotaExcededException("Couta exceded."));

        assertThrows(CuotaExcededException.class, () -> ruleEngine.validateRules("status", "userID"));
    }
}
