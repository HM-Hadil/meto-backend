package com.innovup.meto.request;

import com.innovup.meto.enums.Gender;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@Data
@Jacksonized
@ToString
public class CreateDoctorRequest extends CreateUserRequest {

    @NotNull
    private final Gender gender;

    @NotNull
    private final String address;

    @NotNull
    private final String city;

    @NotNull
    private final List<AcademicLevelRequest> academicLevels;

    @NotNull
    private final List<ExperienceRequest> experiences;

}
