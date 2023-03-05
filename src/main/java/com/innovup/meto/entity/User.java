package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.core.schema.ComSchemaFkConstantName;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends EntityWithSelfAssignedId<UUID> {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(
            name = ComSchemaColumnConstantName.C_ID,
            updatable = false,
            nullable = false
    )
    private UUID id;

    @Column(
            name = ComSchemaColumnConstantName.C_FIRST_NAME,
            length = ComSchemaConstantSize.XL_VALUE
    )
    private String firstname;

    @Column(
            name = ComSchemaColumnConstantName.C_ROLE,
            updatable = false
    )
    @Convert(converter = RoleConverter.class)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = ComSchemaColumnConstantName.C_ROLE,
            referencedColumnName = ComSchemaColumnConstantName.C_CODE,
            foreignKey = @ForeignKey(name = ComSchemaFkConstantName.FK_USER_REF_ROLE),
            nullable = false,
            updatable = false
    )
    private UserRole userRole;

    @Column(
            name = ComSchemaColumnConstantName.C_CREATED_ON
    )
    private LocalDate createdOn;

}
