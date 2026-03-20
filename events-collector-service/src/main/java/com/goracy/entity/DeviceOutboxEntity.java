package com.goracy.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record DeviceOutboxEntity (
        String deviceId,
        LocalDateTime createdAt,
        OutboxStatus status,
        LocalDateTime sentAt,
        long attempts,
        String lastError
) {
    public static DeviceOutboxEntity newEntity(String deviceId) {
        return new DeviceOutboxEntity(
                deviceId,
                LocalDateTime.now(ZoneOffset.UTC),
                OutboxStatus.NEW,
                LocalDateTime.of(1971, 1, 1, 0, 0, 0),
                0,
                ""
        );
    }
}