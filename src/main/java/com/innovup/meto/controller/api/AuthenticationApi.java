package com.innovup.meto.controller.api;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.AuthenticationRequest;
import com.innovup.meto.result.AuthenticationResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(value = "Authentication API Controller", tags = "Auth API")
public interface AuthenticationApi {

    @ApiOperation(value = "Authenticate user", notes = "Authentication operation", response = User.class, tags = {"Authentication API"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authenticated", response = AuthenticationResult.class),
    })
    ResponseEntity<AuthenticationResult> authenticate(AuthenticationRequest authenticationRequest);
}
