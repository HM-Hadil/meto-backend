package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class User extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.C_ID, nullable = false)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_FIRST_NAME, length = ComSchemaConstantSize.XL_VALUE)
    private String firstname;

    @Column(name = ComSchemaColumnConstantName.C_ROLE, nullable = false, length = 1)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Column(name = ComSchemaColumnConstantName.C_CREATED_ON, nullable = false, updatable = false)
    private LocalDate createdOn;

}
