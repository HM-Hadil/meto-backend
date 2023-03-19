package com.innovup.meto.entity;

import javax.persistence.AttributeConverter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class SurgeryDurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        if (duration == null) {
            return null;
        }
        return duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long durationInSeconds) {
        return Duration.of(durationInSeconds, ChronoUnit.SECONDS);
    }
}
