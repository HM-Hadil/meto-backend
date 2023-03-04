package com.innovup.meto.controller;

import com.innovup.meto.request.CreateUserRequest;
import com.innovup.meto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Component
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override

    public ResponseEntity<?> createUser(CreateUserRequest request) {
        log.info("Endpoint '/.../' (POST) called - request = {}", request);
        var response = userService.createNewUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
