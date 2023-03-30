package com.innovup.meto.result;

import com.innovup.meto.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class DoctorResult extends UserResult{

    private Gender gender;

    private String address;

    private String city;

}
