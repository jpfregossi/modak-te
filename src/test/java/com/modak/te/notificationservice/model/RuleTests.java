package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import com.modak.te.notificationservice.service.RedisMessagesBucket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RuleTests {

    @Test
    void shouldExecuteValidation() {
        RedisMessagesBucket enforcer = mock(RedisMessagesBucket.class);
        when(enforcer.validate(anyString(), anyInt(), anyLong())).thenReturn(true);
        FrequencyRuleEntity ruleDTO = new FrequencyRuleEntity();
        ruleDTO.setMessageType("status");
        ruleDTO.setPeriod(1000);
        ruleDTO.setMax(1);
        ruleDTO.setGranularity("RECIPIENT");

        Rule rule = new FrequencyRule(enforcer, ruleDTO);


        assertEquals(true, rule.validate("userID"));
    }
}
