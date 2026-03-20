package com.goracy.mapper;

import com.goracy.avro.DeviceEvent;
import com.goracy.entity.DeviceOutboxEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class DeviceEventMapper {
    public static MapSqlParameterSource toParams(DeviceEvent event) {
        LocalDate eventDate = Instant.ofEpochMilli(event.getTimestamp())
                .atZone(ZoneOffset.UTC)
                .toLocalDate();

        return new MapSqlParameterSource()
                .addValue("device_id", event.getDeviceId())
                .addValue("event_id", event.getEventId())
                .addValue("event_date", eventDate)
                .addValue("timestamp_ms", event.getTimestamp())
                .addValue("type", event.getType())
                .addValue("payload", event.getPayload());
    }

    public static MapSqlParameterSource toParams(DeviceOutboxEntity outbox) {
        return new MapSqlParameterSource()
                .addValue("device_id", outbox.deviceId())
                .addValue("created_at", outbox.createdAt())
                .addValue("status", outbox.status().getValue())
                .addValue("sent_at", outbox.sentAt())
                .addValue("attempts", outbox.attempts())
                .addValue("last_error", outbox.lastError());
    }
}
