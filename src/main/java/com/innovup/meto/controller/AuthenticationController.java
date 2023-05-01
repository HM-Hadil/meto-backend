package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.request.AuthenticationRequest;
import com.innovup.meto.result.AuthenticationResult;
import com.innovup.meto.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Authentication API Controller", tags = "Auth API")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticate all users", response = AuthenticationResult.class, tags = {"Auth API"})
    public ResponseEntity<RestResponse<AuthenticationResult>> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var authenticationResult = authenticationService.authenticate(authenticationRequest);
        if (authenticationResult == null) {
            return new ResponseEntity<>(RestResponse.empty(401, "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(
                RestResponse.of(authenticationService.authenticate(authenticationRequest), 200),
                HttpStatus.OK
        );
    }
}
