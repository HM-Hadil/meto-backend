package com.innovup.meto.request;

import com.innovup.meto.enums.Gender;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@ToString
public abstract class CreateUserRequest {

    @NotNull
    private final String firstname;
    @NotNull
    private final String lastname;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final Gender gender;
}
