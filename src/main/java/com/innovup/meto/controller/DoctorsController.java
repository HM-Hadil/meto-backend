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
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/doctors")
@Api(value = "Doctors API Controller", tags = "Doctors API")
public class DoctorsController extends UsersController<DoctorsService> {

    protected DoctorsController(DoctorsService service) {
        super(service);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates Doctor", response = User.class)
    public ResponseEntity<RestResponse<User>> update(@NotNull @PathVariable UUID id, @NotNull @RequestBody User request) {
        log.info("Endpoint '/doctors/{id}' (PUT) called - id {}, doctor {}", id, request);

        var data = getService().update(id, request);

        log.info("Endpoint '/doctors/{id}' (PUT) finished - id {}, doctor {}", id, request);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping(value = "/surgery/{surgeryId}")
    @ApiOperation(value = "Find all doctors by the chosen surgery type", response = User.class)
    public ResponseEntity<RestResponse<List<User>>> update(@NotNull @PathVariable UUID surgeryId) {
        log.info("Endpoint '/doctors/surgery/{surgeryId}' (GET) called - surgeryId {}", surgeryId);
        // TODO add doctor availability with a specified rendez-vous date of a patient
        var data = getService().findAllDoctorsBySurgeryId(surgeryId);

        log.info("Endpoint '/doctors/surgery/{surgeryId}' (GET) finished - id {}", surgeryId);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }
}
