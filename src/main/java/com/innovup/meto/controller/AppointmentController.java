package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.Appointment;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.request.DevisRequest;
import com.innovup.meto.request.UpdateAppointmentPatient;
import com.innovup.meto.request.UpdateAppointmentRequest;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.AppointmentStatsResult;
import com.innovup.meto.result.DevisResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @ApiOperation(value = "Find all appointments in progress of a doctor", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointmentsInProgressByDoctor(@PathVariable UUID doctorId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.findAllInProgressAppointmentsByDoctor(doctorId);

        return ResponseEntity.ok(data);
    }
    @GetMapping("getAllAppointmentByDoctorAndStatus/{doctorId}")
    @ApiOperation(value = "Find all appointments in progress of a doctor", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<Appointment>> findAllAppointmentsInStatusByDoctor(@PathVariable UUID doctorId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.getAllAppointmentsByDoctorIdWithStatus(doctorId);

        return ResponseEntity.ok(data);
    }
    @GetMapping("getAllAppointmentByChirurgieAndStatus/{chirurgieId}")
    @ApiOperation(value = "Find all appointments by surgery with status", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<Appointment>> findAllAppointmentsInStatusByChirurgie(@PathVariable UUID chirurgieId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.getAllAppointmentsByChirurgieIdWithStatus(chirurgieId);

        return ResponseEntity.ok(data);
    }

    @GetMapping("getAccptedAppointmentByDoctor/{doctorId}")
    @ApiOperation(value = "Find all accepted  appointments of a doctor", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAcceptedAppointmentsByDoctor(@PathVariable UUID doctorId) {
        log.info("Endpoint '/appointments/{doctorId}' (GET) called");

        var data = appointmentService.findAllAccptedAppointmentsByDoctor(doctorId);

        return ResponseEntity.ok(data);
    }
    @GetMapping("getAppointmentByPatient/{patientId}")
    @ApiOperation(value = "Find all appointments of a patient", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<List<AppointmentResult>> findAllAppointmentsByPatient(@PathVariable UUID patientId) {
        log.info("Endpoint '/appointments/{patientId}' (GET) called");

        var data = appointmentService.findAllAppointmentsByPatient(patientId);

        return ResponseEntity.ok(data);
    }
    @PostMapping(path ="createAppointement", produces = MediaType.APPLICATION_JSON_VALUE)
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
@ApiOperation(value = "affecter doctor   - Admin only!", response = AppointmentResult.class, tags = {"Appointment API"})
public ResponseEntity<RestResponse<AppointmentResult>> affecterDoctor(
        @NotNull @PathVariable UUID appointmentId,
        @NotNull @RequestBody UpdateAppointmentRequest request
) {
    log.info("Endpoint '/appointments' (POST) called - request {}", request);

    var response = appointmentService.affectDoctor(appointmentId, request.getDoctorId());

    return ResponseEntity.ok(RestResponse.of(response, 200));
}

    @PutMapping("updateAppointment/{appointmentId}")
    @ApiOperation(value = "update an appointment by id - patient only!", response = AppointmentResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<AppointmentResult>> updateAppointment(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody UpdateAppointmentPatient request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);

        var response = appointmentService.updateAppointment(appointmentId, request);

        return ResponseEntity.ok(RestResponse.of(response, 200));
    }

    @PutMapping("accepterAppointment/{appointmentId}")
    @ApiOperation(value = "accept an appointment by id - doctor only!", response = AppointmentResult.class, tags = {"Doctors API"})
    public ResponseEntity<RestResponse<AppointmentResult>> accepterAppointment(
            @NotNull @PathVariable UUID appointmentId
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", appointmentId);

        var response = appointmentService.acceptAppointment(appointmentId);

        return ResponseEntity.ok(RestResponse.of(response, 200));
    }

    @PutMapping("rejectAppointment/{appointmentId}")
    @ApiOperation(value = "reject an appointment by id - doctor only!", response = AppointmentResult.class, tags = {"Doctors API"})
    public ResponseEntity<RestResponse<AppointmentResult>> rejectAppointment(
            @NotNull @PathVariable UUID appointmentId
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", appointmentId);

        var response = appointmentService.rejectAppointment(appointmentId);

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


    @GetMapping("/appointments/count")
    public List<Object[]> getAppointmentsCountByMonthAndYear() {
        return appointmentService.getAppointmentsCountByMonthAndYear();
    }

    @GetMapping("/appointmentsPerMont/{doctorId}")
    public List<Object[]> getNumAppointmentsByMonthAndDoctor(@PathVariable UUID doctorId) {
        return appointmentService.getNumAppointmentsByMonthAndDoctor(doctorId);
    }


    @GetMapping("doctors/most-frequent")
    public ResponseEntity<DoctorResult> findMostFrequentDoctor() {
        DoctorResult result = appointmentService.findMostFrequentDoctorId();
        return ResponseEntity.ok(result);
    }


    @PostMapping("/{appointmentId}/devis")
    @ApiOperation(value = "create a quotation for surgery and appointment", response = DevisResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<DevisResult>> createDevis(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody DevisRequest request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);


        var response = appointmentService.createAppointmentDevis(appointmentId, request);
        return ResponseEntity.ok(RestResponse.of(response, 200));
    }
    @PutMapping("/{appointmentId}/updateDevis")
    @ApiOperation(value = "update devis by admin", response = DevisResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<DevisResult>> updateDevis(
            @NotNull @PathVariable UUID appointmentId,
            @NotNull @RequestBody DevisRequest request
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}", request);


        var response = appointmentService.updateAppointmentDevis(appointmentId, request);
        return ResponseEntity.ok(RestResponse.of(response, 200));
    }
    @PutMapping("/{appointmentId}/confirmeDevis")
    @ApiOperation(value = "approve devis by patient", response = DevisResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<DevisResult>> confirmeDevis(
            @NotNull @PathVariable UUID appointmentId
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}");


        var response = appointmentService.approveAppointmentDevis(appointmentId);
        return ResponseEntity.ok(RestResponse.of(response, 200));
    }
    @PutMapping("/{appointmentId}/rejectDevis")
    @ApiOperation(value = "reject devis by patient", response = DevisResult.class, tags = {"Appointment API"})
    public ResponseEntity<RestResponse<DevisResult>> rejectDevis(
             @NotNull @PathVariable UUID appointmentId
    ) {
        log.info("Endpoint '/appointments' (POST) called - request {}");


        var response = appointmentService.rejectAppointmentDevis(appointmentId);
        return ResponseEntity.ok(RestResponse.of(response, 200));
    }

    @GetMapping("created-devis")
    @ApiOperation(value = "get created devis by doctor", response = Appointment.class, tags = {"Appointment API"})

    public ResponseEntity<List<Appointment>> getAppointmentsWithCreatedDevis() {
        List<Appointment> appointments = appointmentService.getCreatedAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("updated-devisByAdmin")
    public ResponseEntity<List<Appointment>> getUpdatedDevisByAdmin() {
        List<Appointment> appointments = appointmentService.getUpdateAppointmentsByAdmin();
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("approved-devisByPatient")
    public ResponseEntity<List<Appointment>> getApprovedDEvisByPatient() {
        List<Appointment> appointments = appointmentService.getApproveAppointmentsByPatient();
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("confirmed-devisByPatient/{patientId}")
    public ResponseEntity<List<Appointment>> getConfirmedDEvisByPatientId(@PathVariable UUID patientId) {
        List<Appointment> appointments = appointmentService.getConfirmedAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("confirmed-devisByDoctorId/{doctorId}")
    public ResponseEntity<List<Appointment>> getConfirmedDEvisByDoctorId(@PathVariable UUID doctorId) {
        List<Appointment> appointments = appointmentService.getConfirmedAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("getCreatedDevisById/{appointmentId}")
    public ResponseEntity<Optional<Appointment>> getCreatedDevisById( @PathVariable UUID appointmentId) {
        Optional<Appointment> appointments = appointmentService.getCreatedDevisById(appointmentId);
        return ResponseEntity.ok(appointments);
    }
   @GetMapping("getChangeddDevisByAdmin/{appointmentId}")
    public ResponseEntity<Optional<Appointment>> getChangedDevisByAdmin( @PathVariable UUID appointmentId) {
        Optional<Appointment> appointments = appointmentService.getChangedDevisByAdminById(appointmentId);
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("deleteAppoibtment/{appointmentId}")
    public void deleteappointment(@PathVariable UUID appointmentId){
        this.appointmentService.deleteAppointment(appointmentId);
    }

}

