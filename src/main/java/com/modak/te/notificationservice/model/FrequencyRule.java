package com.modak.te.notificationservice.model;

import com.modak.te.notificationservice.service.RulesEnforcer;
import org.springframework.beans.factory.annotation.Autowired;

public class FrequencyRule extends Rule{
    private String messageType;
    private String granularity;
    private int max;
    private long period;
    private static String VALIDATION_TYPE = "frequency";

    public FrequencyRule(RulesEnforcer enforcer, FrequencyRuleEntity frequencyRuleDTO) {
        super(enforcer);
        this.granularity = frequencyRuleDTO.getGranularity();
        this.messageType = frequencyRuleDTO.getMessageType();
        this.max = frequencyRuleDTO.getMax();
        this.period = frequencyRuleDTO.getPeriod();
    }

    @Override
    public Boolean validate(String userId) {
        String key = messageType + "_" + VALIDATION_TYPE;
        if ("RECIPIENT".equals(granularity)) key = key + "_" + userId;

        return  enforcer.validate(key, max, period);
    }
}
