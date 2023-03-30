package com.innovup.meto.request;

import com.innovup.meto.enums.Gender;
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
public class PatientRequest {

    private final UUID id;

    private final String firstname;

    private final String lastname;

    private final Double weight;

    private final Gender gender;
}
