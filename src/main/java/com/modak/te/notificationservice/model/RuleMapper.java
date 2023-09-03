package com.modak.te.notificationservice.model;


import com.modak.te.notificationservice.service.RulesEnforcer;
import org.springframework.stereotype.Service;

@Service
public class RuleMapper {

      public Rule from(RulesEnforcer enforcer, FrequencyRuleEntity entity) {
            return (Rule) new FrequencyRule(enforcer, entity);
      }
}
