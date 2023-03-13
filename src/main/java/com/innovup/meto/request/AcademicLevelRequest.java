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
public class AcademicLevelRequest {

    @NotNull
    private final String field;

    @NotNull
    private final String diploma;

    @NotNull
    private final String establishment;
}
