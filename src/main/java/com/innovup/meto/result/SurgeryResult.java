package com.innovup.meto.result;

import com.innovup.meto.entity.SurgeryDuration;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class SurgeryResult {

    private UUID id;
    private String name;
    private String description;
    private SurgeryDuration duration;
    private Long durationInSeconds;
    private String image;
}
