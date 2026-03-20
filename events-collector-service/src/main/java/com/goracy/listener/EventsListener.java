package com.goracy.listener;

import com.goracy.avro.DeviceEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventsListener {

    @KafkaListener(
            topics = "device-events",
            groupId = "device-events-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void test(@Payload DeviceEvent event, Acknowledgment ack) {
        System.out.println(event);
        ack.acknowledge();
    }
}
