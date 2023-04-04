package com.innovup.meto.result;

import com.innovup.meto.entity.ChirurgieDuration;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class ChirurgieResult {
    private final UUID id;
    private final String name;
    private final String description;
    private final ChirurgieDuration duration;
    private final Long durationInSeconds;
    private final String image;
}
