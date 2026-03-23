package com.goracy.config;

import com.goracy.avro.DeviceEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class BasicTest extends TestcontainerBaseTest {
    @Autowired
    private KafkaTemplate<String, DeviceEvent> kafkaTemplate;

    @Test
    void testContainersAreRunningAndConfigured() {
        String topic = "device-events";
        DeviceEvent message = new DeviceEvent("321", "123", 0L, "fsf", "fdf");

        kafkaTemplate.send(topic, message);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
    }
}
