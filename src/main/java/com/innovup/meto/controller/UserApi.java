package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateAdminRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;


@Api(value = "User API Controller", tags = "User API")
public interface UserApi {

    @ApiOperation(value = "create User", notes = "This operation will create a new administrator", response = User.class, tags = {"User API"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class),
            //@ApiResponse(code = 404, message = "Resource not found", response = User.class)
    })
    ResponseEntity<?> createAdmin(CreateAdminRequest request);

}
