package com.innovup.meto.result;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.Experience;
import com.innovup.meto.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class DoctorResult extends UserResult{

    private Gender gender;

    private String address;

    private String city;

    private List<AcademicLevel> academicLevels;

    private List<Experience> experiences;

}
