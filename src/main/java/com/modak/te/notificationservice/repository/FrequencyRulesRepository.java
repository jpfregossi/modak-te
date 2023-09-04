package com.modak.te.notificationservice.repository;

import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequencyRulesRepository extends MongoRepository<FrequencyRuleEntity, String> {
    List<FrequencyRuleEntity> getByMessageType(String messageType);
}
