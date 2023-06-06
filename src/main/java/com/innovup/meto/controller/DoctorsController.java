package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.entity.Surgery;
import com.innovup.meto.entity.User;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.service.DoctorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "")
    @ApiOperation(value = "Finds All", response = DoctorResult.class)
    public ResponseEntity<RestResponse<List<DoctorResult>>> findAll() {
        log.info("Endpoint '.../' (GET) called");

        var data = getService().findAll();

        log.info("Endpoint '.../{id}' (GET) finished");

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Finds Doctor by id", response = DoctorResult.class)
    public ResponseEntity<RestResponse<DoctorResult>> findById(@NotNull @PathVariable UUID id) {
        log.info("Endpoint '.../{id}' (GET) called - id {}", id);

        var data = getService().findById(id);

        log.info("Endpoint '.../{id}' (GET) finished - id {}", id);

        if (data == null) {
            return new ResponseEntity<>(RestResponse.empty(404, "User not found"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates Doctor", response = DoctorResult.class)
    public ResponseEntity<RestResponse<DoctorResult>> update(@NotNull @PathVariable UUID id, @NotNull @RequestBody User request) {
        log.info("Endpoint '/doctors/{id}' (PUT) called - id {}, doctor {}", id, request);

        var data = getService().update(id, request);

        log.info("Endpoint '/doctors/{id}' (PUT) finished - id {}, doctor {}", id, request);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping(value = "/surgery/{surgeryId}")
    @ApiOperation(value = "Find doctors by surgery id", response = DoctorResult.class)
    public ResponseEntity<RestResponse<List<DoctorResult>>> update(@NotNull @PathVariable UUID surgeryId) {
        log.info("Endpoint '/doctors/surgery/{surgeryId}' (GET) called - surgeryId {}", surgeryId);

        // TODO add doctor availability with a specified rendez-vous date of a patient

        var data = getService().findAllDoctorsBySurgeryId(surgeryId);

        log.info("Endpoint '/doctors/surgery/{surgeryId}' (GET) finished - id {}", surgeryId);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PostMapping(value = "/{doctorId}/surgery/requested")
    @ApiOperation(value = "Create a surgery request", response = SurgeriesRequest.class)
    public ResponseEntity<RestResponse<SurgeriesRequest>> requestSurgery(
            @NotNull @PathVariable UUID doctorId,
            @NotNull @RequestBody SurgeryRequest request
    ) {
        log.info("Endpoint '/doctors/{id}/surgery/request' (POST) called - doctorId {}, surgeryRequest {}", doctorId, request);

        var data = getService().createSurgeryRequest(doctorId, request);

        log.info("Endpoint '/doctors/{id}/surgery/request' (POST) finished - id {}, surgeryRequest {}", doctorId, request);

        return ResponseEntity.ok(RestResponse.of(data, 201));
    }
}
