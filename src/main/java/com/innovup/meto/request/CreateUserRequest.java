package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@Jacksonized
@ToString
public class CreateUserRequest {

    @NotNull
    private final String firstname;
    @NotNull
    private final String lastname;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
}
