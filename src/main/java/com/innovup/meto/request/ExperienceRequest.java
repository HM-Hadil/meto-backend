package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class ExperienceRequest {

    @NotNull
    private final String specialty;

    @NotNull
    private final String establishment;
}
