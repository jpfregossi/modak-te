package com.modak.te.notificationservice.model;

import lombok.Data;

@Data
public class FrequencyRuleEntity {
    private String messageType;
    private String granularity;
    private int max;
    private long period;
}
