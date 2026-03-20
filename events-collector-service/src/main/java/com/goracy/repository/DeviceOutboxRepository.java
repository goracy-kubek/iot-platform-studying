package com.goracy.repository;

import com.goracy.entity.DeviceOutboxEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.goracy.mapper.DeviceEventMapper.toParams;

@Repository
@RequiredArgsConstructor
public class DeviceOutboxRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public int insert(DeviceOutboxEntity outbox) {
        return jdbc.update("""
            INSERT INTO device_outbox (
                device_id,
                created_at,
                status,
                sent_at,
                attempts,
                last_error
            )
            VALUES (:device_id, :created_at, :status, :sent_at, :attempts, :last_error)
            """, toParams(outbox)
        );
    }
}
