package com.innovup.meto.controller.api;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Api(value = "Registration API Controller", tags = "Registration API")
public interface RegistrationApi {

    @ApiOperation(value = "create User", notes = "This operation will create a new administrator", response = User.class, tags = {"Registration API"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = User.class),})
    ResponseEntity<User> createAdmin(@RequestBody CreateAdminRequest request);


    @ApiOperation(value = "create User", notes = "This operation will create a new Patient", response = User.class, tags = {"Registration API"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = User.class),})
    ResponseEntity<User> createPatient(@RequestBody CreatePatientRequest request);



    @ApiOperation(value = "create User", notes = "This operation will create a new Doctor", response = User.class, tags = {"Registration API"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = User.class),})
    ResponseEntity<User> createDoctor(@RequestBody CreateDoctorRequest request);


}
