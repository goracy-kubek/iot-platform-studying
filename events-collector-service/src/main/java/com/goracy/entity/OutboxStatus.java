package com.goracy.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutboxStatus {
    NEW(0),
    SENT(1);

    private final int value;

    @SuppressWarnings("unused")
    public static OutboxStatus fromValue(int value) {
        return switch (value) {
            case 0 -> NEW;
            case 1 -> SENT;
            default -> throw new IllegalArgumentException("Unknown status: " + value);
        };
    }
}