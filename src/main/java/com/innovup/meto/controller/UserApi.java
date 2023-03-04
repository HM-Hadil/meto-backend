package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/users")
@Api(value = "User API Controller", tags = "User API")
public interface UserApi {

    @ApiOperation(value = "create User", notes = "This operation will create a new user", response = User.class, tags = {"User API"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class)
    })
    @PostMapping(path = {"", "/"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request);
}
