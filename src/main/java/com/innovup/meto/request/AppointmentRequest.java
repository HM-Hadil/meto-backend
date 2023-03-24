package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class AppointmentRequest {

    @NotNull
    private final UUID surgeryId;

    private final UUID doctorId;

    private final String note;

    private final AppointmentPatientRequest patient;
}
