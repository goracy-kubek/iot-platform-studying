package com.goracy.listener;

import com.goracy.avro.DeviceEvent;
import com.goracy.service.DeviceEventsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceEventListener {
    private final DeviceEventsService deviceEventsService;

    @KafkaListener(topics = "device-events")
    public void listenDeviceEvents(@Payload DeviceEvent deviceEvent, Acknowledgment ack) {
        int rowsAffected = deviceEventsService.insert(deviceEvent);
        log.debug("Insert device event. Rows affected: {}", rowsAffected);

        ack.acknowledge();
    }
}
