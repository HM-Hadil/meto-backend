package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.UUID;

@Entity
@Table(name = "surgeries", schema = "public")
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Surgery extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.C_ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_NAME)
    private String name;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION)
    private String description;

    @Column(name = ComSchemaColumnConstantName.C_DURATION)
    @Convert(converter = SurgeryDurationConverter.class)
    private Duration duration;

    @Column(name = ComSchemaColumnConstantName.C_IMAGE)
    private String image;
}
