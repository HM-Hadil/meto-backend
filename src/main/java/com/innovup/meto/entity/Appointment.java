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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Appointment extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID, nullable = false, updatable = false)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.NOTE)
    private String note;

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
    private Surgery surgery;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.PATIENT_ID)
    private User patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ComSchemaColumnConstantName.DOCTOR_ID)
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
