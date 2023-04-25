package com.innovup.meto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = ComSchemaColumnConstantName.C_FIRST_NAME,  nullable = false,length = ComSchemaConstantSize.XL_VALUE)
    private String firstname;

    @Column(name = ComSchemaColumnConstantName.C_LAST_NAME, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String lastname;

    @Column(name = ComSchemaColumnConstantName.C_EMAIL, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String email;

    @Column(name = ComSchemaColumnConstantName.C_PASSWORD, nullable = false, length = ComSchemaConstantSize.XL_VALUE)
    private String password;

    @Column(name = ComSchemaColumnConstantName.C_ADRESSE, length = ComSchemaConstantSize.XL_VALUE)
    private String adresse;

    @Column(name = ComSchemaColumnConstantName.C_VILLE, length = ComSchemaConstantSize.XL_VALUE)
    private String ville;

    @Column(name = ComSchemaColumnConstantName.C_TELEPHONE, length = ComSchemaConstantSize.XL_VALUE)
    private String telephone;


    @Column(name = ComSchemaColumnConstantName.C_IMAGE, length = ComSchemaConstantSize.XL_VALUE)
    @Lob
    private String image;

    @Column(name = ComSchemaColumnConstantName.C_SPECIALITE, length = ComSchemaConstantSize.XL_VALUE)
    private String specialite;

    @Column(name = ComSchemaColumnConstantName.C_GENDER)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    // @Column(name = ComSchemaColumnConstantName.C_IS_ENABLED)
    private boolean isEnabled;


    @Column(name = ComSchemaColumnConstantName.C_ROLE, nullable = false, length = 1)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Column(name = ComSchemaColumnConstantName.CREATED_ON, nullable = false, updatable = false)
    private LocalDate createdOn;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<AcademicLevel> parcours;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Experience> experience;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Appointment> appointments;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
    private List<Chirurgie> specialites;

}
