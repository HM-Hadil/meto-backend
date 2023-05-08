package com.innovup.meto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.AppointmentStatus;
import com.innovup.meto.enums.AppointmentStatusConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments", schema = "public")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Appointment extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID, nullable = false, updatable = false)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.NOTE)
    @Lob
    private String description;

    @Column(name = ComSchemaColumnConstantName.C_IMAGE)
    @Lob
    private  String image;

    @Column(name = ComSchemaColumnConstantName.C_AGE)
    private Double age;

    @Column(name = ComSchemaColumnConstantName.C_WEIGHT)
    private Double weight;

    @Column(name = ComSchemaColumnConstantName.C_dateRdv, nullable = false)
    private  LocalDateTime dateRDV;

    @Column(name = ComSchemaColumnConstantName.C_TYPESANG)
    private String typeSang;

    @Column(name = ComSchemaColumnConstantName.C_TELEPHONE)
    private String phone;

    @Column(name = ComSchemaColumnConstantName.C_VILLE)
    private String ville;

    @Column(name = ComSchemaColumnConstantName.C_ALCOOLIQUE)
    private String alcoolique;

    @Column(name = ComSchemaColumnConstantName.C_TENSION)
    private String tension;

    @Column(name = ComSchemaColumnConstantName.C_DIABETE)
    private String diabete;

    @Column(name = ComSchemaColumnConstantName.C_FUMEE)
    private String fumee;

    @Column(name = ComSchemaColumnConstantName.C_MesureTension)
    private String mesureTension;

    @Column(name = ComSchemaColumnConstantName.C_MesureDiabete)
    private String mesureDiabete;


    @Column(name = ComSchemaColumnConstantName.C_AnalyseDiabete)
    @Lob
    private String analyseDiabete;

    @Column(name = ComSchemaColumnConstantName.C_AutreMaladie)
    private String autreMaladie;

    @Column(name = ComSchemaColumnConstantName.C_DesAutreMaladie)
    private String DesAutreMaladie;

    @Column(name = ComSchemaColumnConstantName.C_AnalyseAutreMaladie)
    @Lob
    private String analyseAutreMaladie;

    @Column(name = ComSchemaColumnConstantName.C_AncienOperation)
    private String ancienOperation;

    @Column(name = ComSchemaColumnConstantName.C_NomAncienOperation)
    private String NomAncienOperation;

    @Column(name = ComSchemaColumnConstantName.C_AnalyseAncienOperation)
    @Lob
    private String AnalyseAncienOperation;

    @Column(name = ComSchemaColumnConstantName.C_AutreAnalyse)
    @Lob
    private String autreAnalyse;

    @Column(name = ComSchemaColumnConstantName.STATUS, nullable = false, length = ComSchemaConstantSize.CODE)
    @Convert(converter = AppointmentStatusConverter.class)
    private AppointmentStatus status;

    @Column(name = ComSchemaColumnConstantName.CREATED_ON, nullable = false)
    private LocalDateTime createdOn;

    @Column(name = ComSchemaColumnConstantName.LAST_UPDATED_ON)
    private LocalDateTime lastUpdatedOn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ComSchemaColumnConstantName.LAST_UPDATED_BY)
    private User lastUpdatedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.SURGERY_ID)
    private Chirurgie surgery;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.PATIENT_ID)
    private User patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.DOCTOR_ID )
    private User doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.ADMINISTRATOR_ID)
    private User administrator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.RENDEZ_VOUS_ID)
    private RendezVous rendezVous;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.DEVIS_ID)
    private Devis devis;

}
