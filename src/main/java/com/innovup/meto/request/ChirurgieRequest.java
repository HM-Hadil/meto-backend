package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class ChirurgieRequest {

    private final String name;

    private final String description;

    private final String image;

    private ChirurgieDurationRequest duration;
}
