package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "academic_level", schema = "public")
@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class AcademicLevel extends EntityWithSelfAssignedId<UUID> {
    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_FIELD)
    private String field;

    @Column(name = ComSchemaColumnConstantName.C_DIPLOMA)
    private String diploma;

    @Column(name = ComSchemaColumnConstantName.C_ESTABLISHMENT)
    private String establishment;
}
