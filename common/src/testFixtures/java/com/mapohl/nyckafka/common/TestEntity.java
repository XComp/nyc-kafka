package com.mapohl.nyckafka.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity implements Entity<Integer> {

    private Integer entityId;
    private String eventTimeStr;
    private int value;

    public TestEntity(int entityId, Instant eventTime, int value) {
        this(entityId, DateTimeFormatter.ISO_INSTANT.format(eventTime), value);
    }

    @Override
    public Instant getEventTime() {
        return Instant.parse(this.eventTimeStr);
    }
}