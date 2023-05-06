package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.User;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.repository.ConfirmationTokenRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.result.PatientResult;
import com.innovup.meto.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
@Api(value = "Registration API Controller", tags = "Registration API")
public class RegistrationController  {


    private final RegistrationService registrationService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    @GetMapping("/checkEmailExists/{email}")
    public ResponseEntity<?> checkEmailExists(@PathVariable String email) {
        boolean exists = registrationService.checkEmailExists(email);
        if (exists) {
            return ResponseEntity.badRequest().body("Email already exists");
        } else {
            return ResponseEntity.ok("Email does not exist");
        }
    }

    @PostMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create User", response = User.class, tags = {"Registration API"})
    public ResponseEntity<RestResponse<AdministratorResult>> createAdmin(@RequestBody CreateAdminRequest request) {

        log.info("Endpoint '/register/admin' (POST) called - request = {}", request);

        var data = registrationService.createNewAdmin(request);

        return new ResponseEntity<>(RestResponse.of(data, 201), HttpStatus.CREATED);
    }

    @PostMapping(path = "/patient", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create User", response = User.class, tags = {"Registration API"})
    public ResponseEntity<RestResponse<PatientResult>> createPatient(@RequestBody CreatePatientRequest request) {

        log.info("Endpoint '/register/patient' (POST) called - request = {}", request);

        var data = registrationService.createNewPatient(request);

        return new ResponseEntity<>(RestResponse.of(data, 201), HttpStatus.CREATED);
    }

    @PostMapping(path = "/doctor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create User", response = User.class, tags = {"Registration API"})
    public ResponseEntity<RestResponse<DoctorResult>> createDoctor(@RequestBody CreateDoctorRequest request) {

        log.info("Endpoint '/register/doctor' (POST) called - request = {}", request);

        var data = registrationService.createNewDoctor(request);

        return new ResponseEntity<>(RestResponse.of(data, 201), HttpStatus.CREATED);
    }

    @GetMapping(path = "/confirm-account")
    @ApiOperation(value = "Confirm a validation token", response = String.class, tags = {"Registration API"})
    public ResponseEntity<String> confirmUserAccount(@RequestParam String token) {
        var confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElse(null);
        if (confirmationToken != null) {
            if (confirmationToken.isExpired()) {
                return ResponseEntity.badRequest().body("Confirmation token expired!");
            }
            var user = userRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(UserNotFoundException::new);
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email!");
    }
}
