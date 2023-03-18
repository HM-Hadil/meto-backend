package com.innovup.meto.users.controller;

import com.innovup.meto.users.controller.Api.UserApi;
import com.innovup.meto.users.entity.User;
import com.innovup.meto.users.request.CreateAdminRequest;
import com.innovup.meto.users.request.CreateDoctorRequest;
import com.innovup.meto.users.request.CreatePatientRequest;
import com.innovup.meto.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @PostMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createAdmin(@RequestBody CreateAdminRequest request) {
        log.info("Endpoint '/users/admin' (POST) called - request = {}", request);
        var response = userService.createNewAdmin(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/patient", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createPatient(@RequestBody CreatePatientRequest request) {
        log.info("Endpoint '/users/patient' (POST) called - request = {}", request);
        var response = userService.createNewPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/doctor", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createDoctor(@RequestBody CreateDoctorRequest request) {
        log.info("Endpoint '/users/doctor' (POST) called - request = {}", request);
        var response = userService.createNewDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(path = "/updateDoctor/{id}" , produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateProfileDoctor(@RequestParam UUID id, @RequestBody CreateDoctorRequest request )
    {
        log.info("Endpoint '/users/updateDoctor' (POST) called - userId = {}, request = {}", id, request);
        User response = userService.updateProfile(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
