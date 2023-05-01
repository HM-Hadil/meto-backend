package com.innovup.meto.result;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.DoctorChirurgie;
import com.innovup.meto.entity.Experience;
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


    private String address;
    private List<AcademicLevel> parcours;

    private List<Experience> experiences;

    private String city;
    private String telephone;
    private String email;
    private String image;
    private String specialite;
    private Long count;

    private List<DoctorChirurgie> surgeries;





}
