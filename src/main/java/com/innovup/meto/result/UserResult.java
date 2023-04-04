package com.innovup.meto.result;

import com.innovup.meto.enums.Gender;
import com.innovup.meto.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString
public abstract class UserResult {
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;
    private Gender gender;

    private Role role;
}
