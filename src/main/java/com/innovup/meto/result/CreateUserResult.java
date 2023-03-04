package com.innovup.meto.result;

import com.innovup.meto.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(setterPrefix = "with")
@Getter
@Jacksonized
@ToString
public class CreateUserResult {

    private final String firstname;
    private final Role role;
}
