package com.innovup.meto.request;

import com.innovup.meto.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Jacksonized
@Validated
@ToString
public class CreateUserRequest {

    @NotNull
    private final String firstname;

    @NotNull
    private final Role role;
}
