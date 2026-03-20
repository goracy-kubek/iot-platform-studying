package com.goracy.repository;

import com.goracy.avro.DeviceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.goracy.mapper.DeviceEventMapper.toParams;

@Repository
@RequiredArgsConstructor
public class DeviceEventRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public int insert(DeviceEvent event) {
        return jdbc.update("""
            INSERT INTO device_events (
                device_id,
                event_id,
                event_date,
                timestamp_ms,
                type,
                payload
            )
            VALUES (:device_id, :event_id, :event_date, :timestamp_ms, :type, :payload)
            """, toParams(event)
        );
    }
}