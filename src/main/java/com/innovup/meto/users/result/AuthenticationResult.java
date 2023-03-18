package com.innovup.meto.users.result;

import com.innovup.meto.users.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResult {
    private UUID id;
    private String email;
    private Role role;
    private String token;
}
