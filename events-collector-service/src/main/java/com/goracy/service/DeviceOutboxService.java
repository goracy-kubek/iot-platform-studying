package com.goracy.service;

import com.goracy.repository.DeviceEventRedisRepository;
import com.goracy.repository.DeviceOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.goracy.entity.DeviceOutboxEntity.newEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceOutboxService {
    private final DeviceOutboxRepository deviceOutboxRepository;
    private final DeviceEventRedisRepository deviceEventRedisRepository;

    /**
     * Insert deviceId in outbox table
     * @param deviceId for saving
     */
    public void insert(String deviceId) {
        if(deviceEventRedisRepository.isDuplicate(deviceId)) {
            log.debug("Duplicate device id: {}", deviceId);
            return;
        }

        int rowsAffected = deviceOutboxRepository.insert(newEntity(deviceId));
        log.debug("Device inserted in outbox table. Rows affected: {}. Device ID: {}", rowsAffected, deviceId);
    }
}
