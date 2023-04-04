package com.innovup.meto.service;

import com.innovup.meto.security.UserPrincipal;
import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.security.providers.JwtTokenProvider;
import com.innovup.meto.result.AuthenticationResult;
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
    private final RegistrationService registrationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationResult authenticate(AuthenticationRequest request) {
        try {
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var userPrincipal = (UserPrincipal) authentication.getPrincipal();
            var user = registrationService.findUserById(userPrincipal.getId());
            var accessToken = jwtTokenProvider.generateToken(userPrincipal);
            return AuthenticationResult.builder()
                    .withEmail(userPrincipal.getEmail())
                    .withId(userPrincipal.getId())
                    .withRole(user.getRole())
                    .withToken(accessToken)
                    .build();
        } catch (Exception e) {
            log.error("Error authentication user email {}", request.getEmail());
            return null;
        }
    }
}
