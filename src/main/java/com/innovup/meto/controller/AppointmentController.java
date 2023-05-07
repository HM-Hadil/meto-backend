package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.request.DevisRequest;
import com.innovup.meto.request.UpdateAppointmentRequest;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.DevisResult;
import com.innovup.meto.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
@Api(value = "Appointment API Controller", tags = "Appointment API")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("")
    @ApiOperation(value = "Find All appointments", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<List<AppointmentResult>>> findAllAppointments() {
        log.info("Endpoint '/appointments' (GET) called");

        var data = appointmentService.findAllAppointments();

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping("/{doctorId}")
    @ApiOperation(value = "Find all appointments of a doctor", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<List<AppointmentResult>>> findAllAppointmentsByDoctor(UUID doctorId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.findAllAppointmentsByDoctor(doctorId);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PostMapping("")
    @ApiOperation(value = "Create a new appointment", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<AppointmentResult>> createAppointment(@NotNull @RequestBody AppointmentRequest request) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        var response = appointmentService.createAppointment(request);

        return ResponseEntity.ok(RestResponse.of(response, 201));
    }

    @PutMapping("/{appointmentId}")
    @ApiOperation(value = "update an appointment by id - Admin only!", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<AppointmentResult>> updateAppointment(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody UpdateAppointmentRequest request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        var response = appointmentService.updateAppointment(appointmentId, request);

        return ResponseEntity.ok(RestResponse.of(response, 200));
    }

    @PutMapping("/{appointmentId}/devis")
    @ApiOperation(value = "create a quotation for surgery and appointment", response = DevisResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<DevisResult>> createDevis(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody DevisRequest request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        if (!request.isApproved()) {
            var response = appointmentService.createAppointmentDevis(appointmentId, request);
            return ResponseEntity.ok(RestResponse.of(response, 200));
        }

        var response = appointmentService.approveAppointmentDevis(appointmentId, request);
        return ResponseEntity.ok(RestResponse.of(response, 200));
    }
}
