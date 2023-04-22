package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "surgeries_requests", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class SurgeriesRequest extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.NAME)
    private String name;

    @Column(name = ComSchemaColumnConstantName.DESCRIPTION)
    private String description;

    @Embedded
    @Column(name = ComSchemaColumnConstantName.DURATION)
    private SurgeryDuration duration;

    @Transient
    private Long durationInSeconds;

    @Column(name = ComSchemaColumnConstantName.IMAGE)
    private String image;

    @ManyToOne
    @JoinColumn(name = ComSchemaColumnConstantName.REQUESTER_ID)
    private User requester;
}
