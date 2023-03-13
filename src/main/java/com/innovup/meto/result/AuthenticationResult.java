package com.innovup.meto.result;

import com.innovup.meto.enums.Role;
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
