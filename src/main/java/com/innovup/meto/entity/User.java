package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaFkConstantName;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(
            name = ComSchemaColumnConstantName.C_ID,
            nullable = false
    )
    private UUID id;

    @Column(
            name = ComSchemaColumnConstantName.C_FIRST_NAME,
            nullable = false
    )
    private String firstname;

    @Column(
            name = ComSchemaColumnConstantName.C_ROLE,
            updatable = false,
            insertable = false
    )
    @Convert(converter = RoleConverter.class)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = ComSchemaColumnConstantName.C_ROLE,
            referencedColumnName = ComSchemaColumnConstantName.C_ROLE,
            foreignKey = @ForeignKey(name = ComSchemaFkConstantName.FK_USER_REF_ROLE),
            nullable = false,
            updatable = false
    )
    private UserRole userRole;

    @Column(
            name = ComSchemaColumnConstantName.C_CREATED_ON,
            nullable = false,
            updatable = false
    )
    private LocalDate createdOn;

}
