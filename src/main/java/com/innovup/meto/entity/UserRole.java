package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
public class UserRole extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.C_ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_ROLE)
    @Convert(converter = RoleConverter.class) /* use @Enumerated(EnumType.STRING) to persist the hole String in DB, default is EnumType.Ordinal */
    private Role role;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION)
    private String description;
}
