package com.innovup.meto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@Jacksonized
@ToString
public class CreateDoctorRequest extends CreateUserRequest {
    @NotNull
    private final String address;

    @NotNull
    private final String city;

    @NotNull
    private final List<AcademicLevelRequest> academicLevels;

    @NotNull
    private final List<ExperienceRequest> experiences;

}
