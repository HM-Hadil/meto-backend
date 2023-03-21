package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.User;
import com.innovup.meto.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class UserController<SERVICE extends UserService> {

    @Getter(AccessLevel.PROTECTED)
    private final SERVICE service;

    @GetMapping(value = "")
    @ApiOperation(value = "Finds All", response = User.class)
    public ResponseEntity<RestResponse<List<User>>> findAll() {
        log.info("Endpoint '.../' (GET) called");

        var data = getService().findAll();

        log.info("Endpoint '.../{id}' (GET) finished");

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Finds one by id", response = User.class)
    public ResponseEntity<RestResponse<User>> findById(@NotNull @PathVariable UUID id) {
        log.info("Endpoint '.../{id}' (GET) called - id {}", id);

        var data = getService().findById(id);

        log.info("Endpoint '.../{id}' (GET) finished - id {}", id);

        if (data == null) {
            return new ResponseEntity<>(RestResponse.empty(404), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

}
