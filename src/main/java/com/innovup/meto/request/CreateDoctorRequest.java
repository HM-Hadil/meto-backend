package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Data
@Jacksonized
@ToString
public class CreateDoctorRequest extends CreateUserRequest {
    @NotNull
    private final String address;

    @NotNull
    private final String city;

    private final List<AcademicLevelRequest> academicLevels;

    private final List<ExperienceRequest> experiences;

    @NotNull
    private final List<UUID> surgeries;

}
