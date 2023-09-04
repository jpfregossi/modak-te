package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.exception.QuotaExcededException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisMessagesBucket implements RulesEnforcer {
    private final RedisTemplate<String, String> redis;

    public RedisMessagesBucket(RedisTemplate<String, String> redis) {
        this.redis = redis;
    }

    @Override
    public Boolean validate(String key, int condition, long ttl) {
        List<String> messageKeysWithinWindow = getKeysWithPattern(key + "*");

        if (messageKeysWithinWindow.size() >= condition) handleRejection(messageKeysWithinWindow);
        else registerMessage(key, ttl);

        return true;
    }

    private List<String> getKeysWithPattern(String pattern) {
        long count = 0;
        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(1000)
                .build();

        Cursor<byte[]> cursor = redis.execute((RedisCallback<Cursor<byte[]>>) connection ->
                connection.scan(options)
        );

        List<String> keys = new ArrayList<>();

        if (cursor != null) {
            while (cursor.hasNext()) {
                byte[] keyBytes = cursor.next();
                String key = new String(keyBytes, StandardCharsets.UTF_8);
                keys.add(key);
            }

            try {
                cursor.close();
            } catch (Exception e) {
                log.error("Closing cursor Error", e);
            }
        }

        return keys;
    }

    private void registerMessage(String key, long ttl) {
            String timestamp = String.valueOf(Instant.now().toEpochMilli());
            redis.opsForValue().set(key + "_" + timestamp, timestamp, ttl, TimeUnit.MILLISECONDS);
    }

    private void handleRejection(List<String> keys) {
        Long ttl = redis.getExpire(keys.stream().sorted().findFirst().get());
        ttl = ttl == 0 ? 1 : ttl;

        throw new QuotaExcededException("Quota exceded. Please wait at least " + ttl + " seconds.");
    }
}
