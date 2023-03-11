package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Api(value = "User API Controller", tags = "User API")
public interface UserApi {

    @ApiOperation(value = "create User", notes = "This operation will create a new administrator", response = User.class, tags = {"User API"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class),
            //@ApiResponse(code = 404, message = "Resource not found", response = User.class)
    })
    @PostMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<User> createAdmin(@RequestBody CreateAdminRequest request);


    @ApiOperation(value = "create User", notes = "This operation will create a new Patient", response = User.class, tags = {"User API"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class),
            //@ApiResponse(code = 404, message = "Resource not found", response = User.class)
    })
    @PostMapping(path = "/patient", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<User> createPatient(@RequestBody CreatePatientRequest request);



    @ApiOperation(value = "create User", notes = "This operation will create a new Doctor", response = User.class, tags = {"User API"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = User.class),
            //@ApiResponse(code = 404, message = "Resource not found", response = User.class)
    })
    @PostMapping(path = "/doctor", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<User> createDoctor(@RequestBody CreateDoctorRequest request);


}
