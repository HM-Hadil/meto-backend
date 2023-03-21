package com.innovup.meto.result;

import com.innovup.meto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResult {
    private String email;
    private Role role;
    private String token;
}
