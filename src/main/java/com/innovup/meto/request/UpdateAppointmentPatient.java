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
public class UpdateAppointmentPatient {
    private final UUID doctorId;
    private final UUID surgeryId;
    private final LocalDateTime rendezVous;
    private final UUID patientId;
    private final String note;
    private final String image;
    private final Double age;
    private final Double weight;
    private final LocalDateTime dateRDV;
    private final String typeSang;
    private final String phone;
    private final String ville;
    private final String alcoolique;
    private final String tension;
    private final String diabete;
    private final String fumee;
    private final String mesureTension;
    private final String mesureDiabete;
    private final String analyseDiabete;
    private final String autreMaladie;
    private final String DesAutreMaladie;
    private final String analyseAutreMaladie;
    private final String ancienOperation;
    private final String NomAncienOperation;
    private final String AnalyseAncienOperation;
    private final String autreAnalyse;

}
