package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class OpinionRequest {

    private final String message;

    private final String image;

    private final UUID patientId;
}
