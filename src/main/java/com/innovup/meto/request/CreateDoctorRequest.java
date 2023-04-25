package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class CreateDoctorRequest extends CreateUserRequest{

    @NotNull
    private final List<ParcoursRequest> parcours;
    @NotNull
    private final List<ExperienceRequest> experience;
    @NotNull
    private String ville;
    @NotNull
    private String adresse;
    @NotNull
    private String  specialite;
    @NotNull
    private String image;
    @NotNull
    private String telephone;


    private final List<UUID> surgeries;

}
