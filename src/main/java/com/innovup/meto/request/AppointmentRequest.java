package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class AppointmentRequest {

    @NotNull
    private final UUID surgeryId;

    @Nullable
    private final UUID doctorId;

    private final String note;
    private final String image;
    private final Double age;
    private final Double weight;
    private final LocalDateTime dateRDV;
    private final String typeSang;
    private final String phone;
    private final String ville;
    private final UUID patientId;

}
