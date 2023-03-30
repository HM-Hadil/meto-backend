package com.innovup.meto.result;

import com.innovup.meto.enums.AppointmentStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class AppointmentResult {

    private UUID id;

    private String note;

    private AppointmentStatus status;

    private LocalDateTime createdOn;

    private SurgeryResult surgery;

    private PatientResult patient;

    private DoctorResult doctor;

    private AdministratorResult administrator;

    private RendezVousResult rendezVous;
}
