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
    @Column(name = ComSchemaColumnConstantName.ID, nullable = false)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.FIRSTNAME,  nullable = false,length = ComSchemaConstantSize.XL_VALUE)
    private String firstname;

    @Column(name = ComSchemaColumnConstantName.LASTNAME, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String lastname;

    @Column(name = ComSchemaColumnConstantName.EMAIL, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String email;

    @Column(name = ComSchemaColumnConstantName.PASSWORD, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String password;

    @Column(name = ComSchemaColumnConstantName.GENDER, nullable = false, length = ComSchemaConstantSize.CODE)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = ComSchemaColumnConstantName.WEIGHT)
    private Double weight;

    @Column(name = ComSchemaColumnConstantName.ADDRESS, length = ComSchemaConstantSize.XL_VALUE)
    private String address;

    @Column(name = ComSchemaColumnConstantName.CITY, length = ComSchemaConstantSize.XL_VALUE)
    private String city;

    @Column(name = ComSchemaColumnConstantName.IS_ENABLED)
    private boolean isEnabled;

    @Column(name = ComSchemaColumnConstantName.CREATED_ON, nullable = false, updatable = false)
    private LocalDate createdOn;

    @Column(name = ComSchemaColumnConstantName.ROLE, nullable = false, length = ComSchemaConstantSize.CODE)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<AcademicLevel> academicLevels;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Experience> experiences;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Appointment> appointments;

    @ManyToMany
    @JoinTable(
            name = "users_surgeries",
            joinColumns = {
                    @JoinColumn(
                            foreignKey = @ForeignKey(name = "fk_user_ref_surgery"),
                            name = ComSchemaColumnConstantName.USER_ID,
                            referencedColumnName = ComSchemaColumnConstantName.ID
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            foreignKey = @ForeignKey(name = "fk_surgery_ref_user"),
                            name = ComSchemaColumnConstantName.SURGERY_ID,
                            referencedColumnName = ComSchemaColumnConstantName.ID
                    )
            }
    )
    private List<Surgery> surgeries;

}
