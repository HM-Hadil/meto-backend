package com.innovup.meto.result;

import com.innovup.meto.entity.SurgeryDuration;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Jacksonized
@ToString
public class SurgeryResult {

    private final UUID id;
    private final String name;
    private final String description;
    private final SurgeryDuration duration;
    private final Long durationInSeconds;
    private final String image;
}
