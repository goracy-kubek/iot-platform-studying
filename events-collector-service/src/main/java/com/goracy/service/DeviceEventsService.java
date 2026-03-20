package com.goracy.service;

import com.goracy.avro.DeviceEvent;
import com.goracy.repository.DeviceEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceEventsService {
    private final DeviceEventRepository deviceEventRepository;

    public int insert(DeviceEvent deviceEvent) {
        return deviceEventRepository.insert(deviceEvent);
    }
}
