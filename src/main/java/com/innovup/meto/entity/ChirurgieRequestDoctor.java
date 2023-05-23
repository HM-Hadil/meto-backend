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

public class ChirurgieRequestDoctor extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_NAME)
    private String name;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION)
    private String description;

    @Embedded
    @Column(name = ComSchemaColumnConstantName.C_DURATION)
    private ChirurgieDuration duration;

    @Transient
    private Long durationInSeconds;

    @Column(name = ComSchemaColumnConstantName.C_IMAGE)
    private String image;

    @ManyToOne
    @JoinColumn(name = ComSchemaColumnConstantName.REQUESTER_ID)
    private User requester;

    @Column(name = "status")
    private String status;
}