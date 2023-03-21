package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.User;
import com.innovup.meto.service.DoctorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/doctors")
@Api(value = "Doctors API Controller", tags = "Doctors API")

public class DoctorsController extends UserController<DoctorsService> {

    protected DoctorsController(DoctorsService service) {
        super(service);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates Doctor", response = User.class)
    public ResponseEntity<RestResponse<User>> update(@NotNull @PathVariable UUID id, @NotNull @RequestBody User request) {
        log.info("Endpoint '.../{id}' (PUT) called - id {}, doctor {}", id, request);

        var data = getService().update(id, request);

        log.info("Endpoint '.../{id}' (PUT) finished - id {}, doctor {}", id, request);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }
}
