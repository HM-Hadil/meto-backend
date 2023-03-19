package com.innovup.meto.mapper;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurgeryMapper {

    public static SurgeryResult durationToSeconds(Surgery surgery) {
        return SurgeryResult.builder()
                .withId(surgery.getId())
                .withName(surgery.getName())
                .withDescription(surgery.getDescription())
                .withDuration(surgery.getDuration())
                .withDurationInSeconds(DurationConverter.toSeconds(surgery.getDuration()))
                .withImage(surgery.getImage())
                .build();
    }
}
