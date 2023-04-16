package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class UpdateAppointmentRequest {

    private final UUID doctorId;
    private final UUID surgeryId;
    private final LocalDateTime rendezVous;
    private final UUID adminId;
}