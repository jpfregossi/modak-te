package com.modak.te.notificationservice.model;


import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import com.modak.te.notificationservice.service.RulesEnforcer;
import org.springframework.stereotype.Service;

@Service
public class RuleFactoryImpl implements RuleFactory {
      private final RulesEnforcer enforcer;

      public RuleFactoryImpl(RulesEnforcer enforcer) {
            this.enforcer = enforcer;
      }

      public Rule from(FrequencyRuleEntity entity) {
            return new FrequencyRule(enforcer, entity);
      }
}
