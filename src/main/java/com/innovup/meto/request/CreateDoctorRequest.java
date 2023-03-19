package com.innovup.meto.request;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.Experience;
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
    private Gender gender;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private List<AcademicLevel> academicLevels;
    @NotNull
    private List<Experience> experiences;

}
