package com.modak.te.notificationservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "frequency-rules")
public class FrequencyRuleEntity {
    @Id
    private String id;
    private String messageType;
    private String granularity;
    private int max;
    private long period;
}