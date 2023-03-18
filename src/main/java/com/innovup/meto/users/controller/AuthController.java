package com.innovup.meto.users.controller;

import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.users.controller.Api.AuthenticationApi;
import com.innovup.meto.users.result.AuthenticationResult;
import com.innovup.meto.users.service.AuthenticationService;
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
@RequestMapping("")
public class AuthController  implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResult> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
    }


}