package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "academic_level", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicLevel extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.FIELD)
    private String field;

    @Column(name = ComSchemaColumnConstantName.DIPLOMA)
    private String diploma;

    @Column(name = ComSchemaColumnConstantName.ESTABLISHMENT)
    private String establishment;

}
