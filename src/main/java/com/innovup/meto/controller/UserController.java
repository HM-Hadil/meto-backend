package com.innovup.meto.controller;

import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @PostMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest request) {
        log.info("Endpoint '/.../' (POST) called - request = {}", request);
        var response = userService.createNewAdmin(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/patient", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientRequest request) {
        log.info("Endpoint '/.../' (POST) called - request = {}", request);
        var response = userService.createNewPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/doctor", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDoctor(@RequestBody CreateDoctorRequest request) {
        log.info("Endpoint '/.../' (POST) called - request = {}", request);
        var response = userService.createNewDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
