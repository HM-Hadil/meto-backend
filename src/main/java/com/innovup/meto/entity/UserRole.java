package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_USER_ROLE_CODE",
                        columnNames = {ComSchemaColumnConstantName.C_CODE, ComSchemaColumnConstantName.C_ROLE}
                )}
)
@Getter
@Setter
@ToString
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRole extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.C_ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.C_CODE, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String code;

    @Column(name = ComSchemaColumnConstantName.C_ROLE)
    @Convert(converter = RoleConverter.class) /* use @Enumerated(EnumType.STRING) to persist the hole String in DB, default is EnumType.Ordinal */
    private Role role;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION)
    private String description;
}
