package com.innovup.meto.users.service;

import com.innovup.meto.security.UserPrincipal;
import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.security.providers.JwtTokenProvider;
import com.innovup.meto.users.result.AuthenticationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthenticationResult authenticate(AuthenticationRequest request) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userPrincipal = (UserPrincipal) authentication.getPrincipal();
            var user = userService.findById(userPrincipal.getId());
            var accessToken = jwtTokenProvider.generateToken(userPrincipal);
            return AuthenticationResult.builder()
                    .withId(userPrincipal.getId())
                    .withEmail(userPrincipal.getEmail())
                    .withRole(user.getRole())
                    .withToken(accessToken)
                    .build();
        } catch (Exception e) {
            log.error("Error authentication user email {}", request.getEmail());
            throw new RuntimeException(e.getMessage());
        }
    }
}
