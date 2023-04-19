package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.request.UpdateAppointmentRequest;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.AppointmentStatsResult;
import com.innovup.meto.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/appointments/")
@Api(value = "Appointment API Controller", tags = "Appointment API")
public class AppointmentController {


    private final AppointmentService appointmentService;

    @GetMapping("")
    @ApiOperation(value = "Find All appointments", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointments() {
        log.info("Endpoint '/appointments' (GET) called");

        var data = appointmentService.findAllAppointments();

        return ResponseEntity.ok(data);
    }
    @GetMapping("appointmentsWithoutdoctor")
    @ApiOperation(value = "Find All appointments with doctor null", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointmentsByDoctorNull() {
        log.info("Endpoint '/appointments By Doctor null' (GET) called");

        var data = appointmentService.findAllAppointmentsByDoctorIdNull();

        return ResponseEntity.ok(data);
    }

    @GetMapping("getAppointmentByDoctor/{doctorId}")
    @ApiOperation(value = "Find all appointments of a doctor", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointmentsByDoctor(@PathVariable UUID doctorId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.findAllAppointmentsByDoctor(doctorId);

        return ResponseEntity.ok(data);
    }
    @GetMapping("getAppointmentByPatient/{patientId}")
    @ApiOperation(value = "Find all appointments of a patient", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointmentsByPatient(@PathVariable UUID patientId) {
        log.info("Endpoint '/appointments/{patientId}' (GET) called");

        var data = appointmentService.findAllAppointmentsByPatient(patientId);

        return ResponseEntity.ok(data);
    }
    @PostMapping("createAppointement")
    @ApiOperation(value = "Create a new appointment", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<AppointmentResult>> createAppointment(@RequestBody AppointmentRequest request) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        var response = appointmentService.createAppointment(request);

        return new ResponseEntity<>(RestResponse.of(response, 201), HttpStatus.CREATED);
    }

    @GetMapping("getAppointmentById/{id}")
    @ApiOperation(value = "Find  appointments By id", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<Optional<AppointmentResult>> findAppointmentById(@PathVariable UUID id) {
        log.info("Endpoint '/appointments/{id}' (GET) called");

        var data = appointmentService.findAppointmentById(id);

        return ResponseEntity.ok(data);
    }


    @PutMapping("affecterMedecin/{appointmentId}")
    @ApiOperation(value = "update an appointment by id - Admin only!", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<AppointmentResult>> updateAppointment(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody UpdateAppointmentRequest request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        var response = appointmentService.updateAppointment(appointmentId, request);

        return ResponseEntity.ok(RestResponse.of(response, 200));
    }

    @GetMapping("/mostFrequentSurgeryId")
    @ApiOperation(value = "get the most frequent surgery", response = AppointmentResult.class, tags = {"Appointment API"})

    public List<Object[]> findMostFrequentSurgeryWithNameAndImageAndCount() {
        return appointmentService.findMostFrequentSurgeryWithNameAndImageAndCount();
    }

    @GetMapping("/mostFrequentDoctorId")
    @ApiOperation(value = "get the most frequent doctor", response = AppointmentResult.class, tags = {"Appointment API"})

    public List<Object[]> findMostFrequentUserWithNameAndImageAndCount() {
        return appointmentService.findMostFrequentUserWithNameAndImageAndCount();
    }


    @GetMapping("/mostFrequentCity")
    public List<Object[]>  getMostFrequentCity() {

        return   appointmentService.getMostFrequentCity();
    }



    @GetMapping("/getAppointmentStatsByDoctor/{doctorId}")
    @ApiOperation(value = "Get appointment statistics by doctor", response = AppointmentStatsResult.class, tags = {"Appointment API"})
    public ResponseEntity<AppointmentStatsResult> getAppointmentStatsByDoctor(@PathVariable UUID doctorId) {
        log.info("Endpoint '/appointments/getAppointmentStatsByDoctor/{doctorId}' (GET) called");

        AppointmentStatsResult statsResult = appointmentService.findAppointmentStatsByDoctor(doctorId);
        if (statsResult != null) {
            return ResponseEntity.ok(statsResult);
        } else {
            // Return appropriate response when no data found
            return ResponseEntity.notFound().build();
        }
    }
}
