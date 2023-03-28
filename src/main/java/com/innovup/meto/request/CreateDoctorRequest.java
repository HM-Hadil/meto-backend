package com.innovup.meto.request;

import com.innovup.meto.enums.Gender;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class CreateDoctorRequest extends CreateUserRequest{

    @NotNull
    private final Gender gender;
    @NotNull
    private final List<ParcoursRequest> parcours;
    @NotNull
    private final List<ExperienceRequest> experience;
    @NotNull
    private String ville;
    @NotNull
    private String adresse;
    @NotNull
    private String specialite;
    @NotNull
    private String image;
    @NotNull
    private String telephone;

}
