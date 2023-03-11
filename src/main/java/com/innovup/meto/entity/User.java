package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import com.innovup.meto.core.schema.ComSchemaConstantSize;
import com.innovup.meto.enums.Role;
import com.innovup.meto.enums.RoleConverter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
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
public class User extends EntityWithSelfAssignedId<UUID> implements UserDetails {

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

    @Column(name = ComSchemaColumnConstantName.C_PARCOURS, length = ComSchemaConstantSize.XL_VALUE)
    private String parcours;

    @Column(name = ComSchemaColumnConstantName.C_EXPERIENCE, length = ComSchemaConstantSize.XL_VALUE)
    private String experience;

    @Column(name = ComSchemaColumnConstantName.C_ADRESSE, length = ComSchemaConstantSize.XL_VALUE)
    private String adresse;

    @Column(name = ComSchemaColumnConstantName.C_VILLE, length = ComSchemaConstantSize.XL_VALUE)
    private String ville;

    @Column(name = ComSchemaColumnConstantName.C_IMAGE, length = ComSchemaConstantSize.XL_VALUE)
    private String image;

    @Column(name = ComSchemaColumnConstantName.C_SPECIALITE, length = ComSchemaConstantSize.XL_VALUE)
    private String specialite;

    @Column(name = ComSchemaColumnConstantName.C_SEXE, length = ComSchemaConstantSize.XL_VALUE)
    private String sexe;



    @Column(name = ComSchemaColumnConstantName.C_ROLE, nullable = false, length = 1)
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Column(name = ComSchemaColumnConstantName.C_CREATED_ON, nullable = false, updatable = false)
    private LocalDate createdOn;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
