package com.innovup.meto.users.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class CreateDoctorRequest {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String sexe;
    @NotNull
    private String parcours;
    @NotNull
    private String experience;
    @NotNull
    private String ville;
    @NotNull
    private String adresse;
    @NotNull
    private String specialite;
    @NotNull
    private String image;
}
