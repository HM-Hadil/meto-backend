package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patient_opinion", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor

public class PatientOpinion extends EntityWithSelfAssignedId<UUID> {
    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_IMAGE)
    @Lob
    private String image;

    @Column(name = ComSchemaColumnConstantName.C_MESSAGE)
    @Lob
    private String message;

    @Column(name = ComSchemaColumnConstantName.C_IS_ENABLED)
    private boolean isEnabled;

    @Column(name = ComSchemaColumnConstantName.CREATED_ON, nullable = false)
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = ComSchemaColumnConstantName.REQUESTER_ID)
    private User patient;

}
