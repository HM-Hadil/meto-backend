package com.innovup.meto.result;

import com.innovup.meto.enums.AppointmentStatus;
import com.innovup.meto.pojo.Administrator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    private String image;
    private final Double age;
    private PatientResult patient;
    private Administrator lastUpdatedBy;
    private LocalDateTime lastUpdatedOn;
    private final String ville;
    private final Double weight;
    private final LocalDateTime dateRDV;
    private final String typeSang;
    private final String phone;
    private AppointmentStatus status;
    private LocalDateTime createdOn;
    private ChirurgieResult surgery;
    private DoctorResult doctor;
    private AdministratorResult administrator;
    private RendezVousResult rendezVous;
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
