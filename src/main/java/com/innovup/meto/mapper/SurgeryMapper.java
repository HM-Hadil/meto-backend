package com.innovup.meto.mapper;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.result.DoctorSurgery;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class SurgeryMapper {

    public SurgeryResult entityToResult(Surgery surgery) {
        return SurgeryResult.builder()
                .withId(surgery.getId())
                .withName(surgery.getName())
                .withDescription(surgery.getDescription())
                .withDuration(surgery.getDuration())
                .withDurationInSeconds(DurationConverter.toSeconds(surgery.getDuration()))
                .withImage(surgery.getImage())
                .build();
    }

    public DoctorSurgery entityToName(Surgery surgery) {
        return DoctorSurgery.builder()
                .withId(surgery.getId())
                .withName(surgery.getName())
                .build();
    }
}
