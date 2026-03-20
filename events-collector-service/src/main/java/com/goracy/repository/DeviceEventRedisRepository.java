package com.goracy.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class DeviceEventRedisRepository {
    private final StringRedisTemplate redis;
    private final Duration ttl;

    public DeviceEventRedisRepository(
            StringRedisTemplate redis,
            @Value("${dedup.ttl:10m}") Duration ttl
    ) {
        this.redis = redis;
        this.ttl = ttl;
    }

    /**
     * Method for deduplication event by deviceId
     * @param deviceId for checking
     */
    public boolean isDuplicate(String deviceId) {
        return Boolean.FALSE.equals(
                redis
                    .opsForValue()
                    .setIfAbsent("dedup:" + deviceId, "1", ttl)
        );
    }
}