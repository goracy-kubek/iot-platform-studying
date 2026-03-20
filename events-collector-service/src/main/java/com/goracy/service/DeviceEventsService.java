package com.goracy.service;

import com.goracy.avro.DeviceEvent;
import com.goracy.repository.DeviceEventRedisRepository;
import com.goracy.repository.DeviceEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceEventsService {
    private final DeviceEventRedisRepository deviceEventRedisRepository;
    private final DeviceEventRepository deviceEventRepository;

    public void insert(DeviceEvent deviceEvent) {
        if(deviceEventRedisRepository.isDuplicate(deviceEvent.getDeviceId())) {
            return;
        }

        deviceEventRepository.insert(deviceEvent);
    }
}
