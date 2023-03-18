package com.innovup.meto.users.controller.Api;

import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.users.result.AuthenticationResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "Authentication API Controller", tags = "Auth API")

public interface AuthenticationApi {


    @ApiOperation(value = "Authenticate user", notes = "Authentication operation", response = AuthenticationResult.class, tags = {"Auth API"})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Authenticated", response = AuthenticationResult.class) })
    ResponseEntity<AuthenticationResult> authenticate(AuthenticationRequest authenticationRequest);
}
