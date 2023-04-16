package com.innovup.meto.pojo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder(setterPrefix = "with")
@Jacksonized
public class Administrator {

    private final String firstname;
    private final String lastname;
    private final String email;
}
