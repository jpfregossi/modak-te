package com.modak.te.notificationservice.repository;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequencyRulesRepository extends MongoRepository<FrequencyRuleEntity, String> {
    //@Cacheable(value = "rules", key = "#messageType")
    List<FrequencyRuleEntity> getByMessageType(String messageType);
}
