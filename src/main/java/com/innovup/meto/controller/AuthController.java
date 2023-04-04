package com.innovup.meto.controller;

import com.innovup.meto.result.AuthenticationResult;
import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("")
@Api(value = "Authentication API Controller", tags = "Auth API")
public class AuthController  {


    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticate user", response = AuthenticationResult.class, tags = {"Auth API"})
    public ResponseEntity<AuthenticationResult> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var authenticationResult = authenticationService.authenticate(authenticationRequest);

        return new ResponseEntity<>(authenticationService.authenticate(authenticationRequest),
                HttpStatus.OK
        );
    }

}