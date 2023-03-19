package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "experience", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Experience extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.C_ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_SPECIALTY)
    private String specialty;

    @Column(name = ComSchemaColumnConstantName.C_ESTABLISHMENT)
    private String establishment;

}
