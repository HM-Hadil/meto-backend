package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.Gender;
import com.innovup.meto.enums.GenderConverter;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "public")
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

    @Column(name = ComSchemaColumnConstantName.C_FIRST_NAME,  nullable = false,length = ComSchemaConstantSize.XL_VALUE)
    private String firstname;

    @Column(name = ComSchemaColumnConstantName.C_LAST_NAME, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String lastname;

    @Column(name = ComSchemaColumnConstantName.C_EMAIL, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String email;

    @Column(name = ComSchemaColumnConstantName.C_PASSWORD, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String password;

    @Column(name = ComSchemaColumnConstantName.C_GENDER)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = ComSchemaColumnConstantName.C_ADDRESS, length = ComSchemaConstantSize.XL_VALUE)
    private String address;

    @Column(name = ComSchemaColumnConstantName.C_CITY, length = ComSchemaConstantSize.XL_VALUE)
    private String city;

    @Column(name = ComSchemaColumnConstantName.C_IS_ENABLED)
    private boolean isEnabled;

    @Column(name = ComSchemaColumnConstantName.C_CREATED_ON, nullable = false, updatable = false)
    private LocalDate createdOn;

    @Column(name = ComSchemaColumnConstantName.C_ROLE, nullable = false, length = 1)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<AcademicLevel> academicLevels;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Experience> experiences;

}
