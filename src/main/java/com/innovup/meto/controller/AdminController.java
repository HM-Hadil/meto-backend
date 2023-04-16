package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.service.AdminService;
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
@RequestMapping("/admins")
@Api(value = "Administrator API Controller", tags = "Admin API")
public class AdminController extends UserController<AdminService>{

    protected AdminController(AdminService service) {
        super(service);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Finds All", response = AdministratorResult.class)
    public ResponseEntity<RestResponse<List<AdministratorResult>>> findAll() {
        log.info("Endpoint '.../' (GET) called");

        var data = getService().findAll();

        log.info("Endpoint '.../{id}' (GET) finished");

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }
    @GetMapping(value = "getAdminById/{id}")
    @ApiOperation(value = "Finds Admin by id", response = AdministratorResult.class)
    public ResponseEntity<RestResponse<AdministratorResult>> findById(@NotNull @PathVariable UUID id) {
        log.info("Endpoint '.../{id}' (GET) called - id {}", id);

        var data = getService().findById(id);

        log.info("Endpoint '.../{id}' (GET) finished - id {}", id);

        if (data == null) {
            return new ResponseEntity<>(RestResponse.empty(404, "User not found"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PatchMapping(value = "/{id}/validate/{appointmentId}")
    @ApiOperation(value = "Validates an appointment", response = AppointmentResult.class)
    public ResponseEntity<RestResponse<AppointmentResult>> validateAppointment(
            @NotNull @PathVariable UUID id,
            @NotNull @PathVariable UUID appointmentId
    ) {
        log.info(
                "Endpoint '.../{id}/validate/{appointmentId}/{doctorId}' (PATCH) called - id {}, appointmentId {}",
                id, appointmentId
        );

        var data = getService().validateAppointment(id, appointmentId);

        log.info("Endpoint '.../{id}/validate/{appointmentId}/{doctorId}' (PATCH) finished - id {}, appointmentId {}",
                id, appointmentId
        );

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PatchMapping(value = "/{id}/validate/{appointmentId}/{doctorId}")
    @ApiOperation(value = "Validates an appointment with the chosen doctor", response = AppointmentResult.class)
    public ResponseEntity<RestResponse<AppointmentResult>> validateAppointmentWithDoctorId(
            @NotNull @PathVariable UUID id,
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @PathVariable UUID doctorId
    ) {
        log.info(
                "Endpoint '.../{id}/validate/{appointmentId}/{doctorId}' (PATCH) called - id {}, appointmentId {}, doctorId {}",
                id, appointmentId, doctorId
        );

        var data = getService().validateAppointmentWithDoctorId(id, appointmentId, doctorId);

        log.info("Endpoint '.../{id}/validate/{appointmentId}/{doctorId}' (PATCH) finished - id {}, appointmentId {}, doctorId {}",
                id, appointmentId, doctorId
        );

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

}
