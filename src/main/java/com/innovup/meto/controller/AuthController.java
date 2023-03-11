package com.innovup.meto.controller;

import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.security.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    @Override
    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(new AuthenticationResponse(), HttpStatus.ACCEPTED);
    }
}
