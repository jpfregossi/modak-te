package com.modak.te.notificationservice.service;

import com.modak.te.notificationservice.config.TestRedisConfiguration;
import com.modak.te.notificationservice.exception.QuotaExcededException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = TestRedisConfiguration.class)
public class RedisMessagesBucketTests {
    @Autowired
    RedisMessagesBucket bucket;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @AfterEach
    public void cleanup() throws Exception {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    public void shouldValidateMessage() {
        Boolean isValid = bucket.validate("status_frequency_846975", 2, 1000);

        assertEquals(true, isValid);
    }

    @Test
    void shouldNotValidateMessage() throws InterruptedException {
        bucket.validate("status_frequency_846975", 2, 10000);
        Thread.sleep(5000);
        bucket.validate("status_frequency_846975", 2, 10000);

        Exception exception = assertThrows(QuotaExcededException.class, () -> bucket.validate("status_frequency_846975", 2, 1000));

        assertEquals("Quota exceded. Please wait at least 5 seconds.", exception.getMessage());
    }


    @Test
    void shouldValidateMessageAfterWindowPasses() throws InterruptedException {
        bucket.validate("status_frequency_846975", 2, 1000);
        bucket.validate("status_frequency_846975", 2, 1000);
        Thread.sleep(1000);

        Boolean isValid = bucket.validate("status_frequency_846975", 1, 1000);

        assertEquals(true, isValid);
    }
}
