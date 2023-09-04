package com.modak.te.notificationservice.config;


import com.modak.te.notificationservice.entity.FrequencyRuleEntity;
import com.modak.te.notificationservice.repository.FrequencyRulesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoInitializer {

    @Bean
    public CommandLineRunner initDatabase(FrequencyRulesRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                FrequencyRuleEntity rule1 = new FrequencyRuleEntity();
                rule1.setGranularity("RECIPIENT");
                rule1.setPeriod(60000);
                rule1.setMax(2);
                rule1.setMessageType("status");
                repo.save(rule1);

                FrequencyRuleEntity rule2 = new FrequencyRuleEntity();
                rule2.setGranularity("RECIPIENT");
                rule2.setPeriod(86400000);
                rule2.setMax(1);
                rule2.setMessageType("news");
                repo.save(rule2);

                FrequencyRuleEntity rule3 = new FrequencyRuleEntity();
                rule3.setGranularity("RECIPIENT");
                rule3.setPeriod(3600000);
                rule3.setMax(3);
                rule3.setMessageType("marketing");
                repo.save(rule3);
            }
        };
    }

    private void createCollections(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.collectionExists(FrequencyRuleEntity.class)) {
            mongoTemplate.createCollection(FrequencyRuleEntity.class);
        }
    }
}