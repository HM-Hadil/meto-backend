package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.RendezVousStatus;
import com.innovup.meto.enums.RendezVousStatusConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rendez_vous", schema = "public")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RendezVous extends EntityWithSelfAssignedId<UUID> {
    @Id
    @Column(name = ComSchemaColumnConstantName.ID, nullable = false, updatable = false)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.STATUS, nullable = false, length = ComSchemaConstantSize.CODE)
    @Convert(converter = RendezVousStatusConverter.class)
    private RendezVousStatus status;

    @Column(name = ComSchemaColumnConstantName.C_dateRdv, nullable = false)
    private LocalDateTime dateRDV;

    @Column(name = ComSchemaColumnConstantName.CREATED_ON, nullable = false)
    private LocalDateTime createdOn;

    @Column(name = ComSchemaColumnConstantName.LAST_UPDATED_ON)
    private LocalDateTime lastUpdatedOn;
}
