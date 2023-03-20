package com.innovup.meto.controller;

import com.innovup.meto.controller.api.RegistrationApi;
import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class RegistrationController implements RegistrationApi {

    private final RegistrationService registrationService;

    @Override
    @PostMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createAdmin(@RequestBody CreateAdminRequest request) {
        log.info("Endpoint '/register/admin' (POST) called - request = {}", request);
        var response = registrationService.createNewAdmin(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/patient", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createPatient(@RequestBody CreatePatientRequest request) {
        log.info("Endpoint '/register/patient' (POST) called - request = {}", request);
        var response = registrationService.createNewPatient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping(path = "/doctor", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createDoctor(@RequestBody CreateDoctorRequest request) {
        log.info("Endpoint '/register/doctor' (POST) called - request = {}", request);
        var response = registrationService.createNewDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
