package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.exception.QuotaExcededException;
import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import com.modak.te.notificationservice.model.Rule;
import com.modak.te.notificationservice.model.RuleFactoryImpl;
import com.modak.te.notificationservice.repository.FrequencyRulesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class RuleEngineImplTests {
    @Test
    void shouldValidateEveryRule() {
        FrequencyRulesRepository repo = mock(FrequencyRulesRepository.class);
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
        RuleFactoryImpl mapper = mock(RuleFactoryImpl.class);

        RuleEngineImpl ruleEngine = new RuleEngineImpl(repo, mapper);

        when(repo.getByMessageType(anyString())).thenReturn(entities);
        when(mapper.from(ruleDTO)).thenReturn(rule);

        ruleEngine.validateRules("status", "userID");

        verify(rule, times(3)).validate("userID");
    }

    @Test
    void shouldThrowExceptionWhenTheLastRuleDoesntValidate() {
        FrequencyRulesRepository repo = mock(FrequencyRulesRepository.class);
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
        RuleFactoryImpl mapper = mock(RuleFactoryImpl.class);

        RuleEngineImpl ruleEngine = new RuleEngineImpl(repo, mapper);

        when(repo.getByMessageType(anyString())).thenReturn(entities);
        when(mapper.from(ruleDTO)).thenReturn(rule);
        when(mapper.from(ruleDTO2)).thenReturn(rule2);

        when(rule.validate("userID")).thenReturn(true);
        when(rule2.validate("userID")).thenThrow(new QuotaExcededException("Couta exceded."));

        assertThrows(QuotaExcededException.class, () -> ruleEngine.validateRules("status", "userID"));
    }
}
