package com.goracy.service;

import com.goracy.avro.DeviceEvent;
import com.goracy.repository.DeviceEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceEventsService {
    private final DeviceOutboxService deviceOutboxService;
    private final DeviceEventRepository deviceEventRepository;

    /**
     * Save device event and device id into the DB
     * @param deviceEvent for saving
     */
    public void insert(DeviceEvent deviceEvent) {
        int rowsAffected = deviceEventRepository.insert(deviceEvent);
        log.debug("Event inserted. Rows affected: {}. Event ID: {}", rowsAffected, deviceEvent.getEventId());

        deviceOutboxService.insert(deviceEvent.getDeviceId());
    }
}
