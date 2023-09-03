package com.modak.te.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisProperties {
    private int port;
    private String host;

    public RedisProperties(
            @Value("${spring.redis.port}") int redisPort,
            @Value("${spring.redis.host}") String redisHost) {
        this.port = redisPort;
        this.host = redisHost;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}