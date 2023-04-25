package com.innovup.meto.mapper;

import com.innovup.meto.entity.Chirurgie;
import com.innovup.meto.entity.DoctorChirurgie;
import com.innovup.meto.result.ChirurgieResult;
import com.innovup.meto.util.DurationConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ChirurgieMapper {

    public static ChirurgieResult entityToResult(Chirurgie chirugie) {
        return ChirurgieResult.builder()
                .withId(chirugie.getId())
                .withName(chirugie.getName())
                .withDescription(chirugie.getDescription())
                .withDuration(chirugie.getDuration())
                .withDurationInSeconds(DurationConverter.toSeconds(chirugie.getDuration()))
                .withImage(chirugie.getImage())
                .build();
    }


    public DoctorChirurgie entityToName(Chirurgie surgery) {
        return DoctorChirurgie.builder()
                .withId(surgery.getId())
                .withName(surgery.getName())
                .build();
    }
}
