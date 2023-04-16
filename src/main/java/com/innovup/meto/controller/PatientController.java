package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.result.PatientResult;
import com.innovup.meto.service.PatientService;
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
@RequestMapping("/patients")
@Api(value = "Patient API Controller", tags = "Patient API")
public class PatientController  extends UserController<PatientService> {
    protected PatientController(PatientService service) {
        super(service);
    }


    @GetMapping(value = "")
    @ApiOperation(value = "Finds All", response = PatientResult.class)
    public ResponseEntity<RestResponse<List<PatientResult>>> findAll() {
        log.info("Endpoint '.../' (GET) called");

        var data = getService().findAll();

        log.info("Endpoint '.../{id}' (GET) finished");

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Finds Patient by id", response = PatientResult.class)
    public ResponseEntity<RestResponse<PatientResult>> findById(@NotNull @PathVariable UUID id) {
        log.info("Endpoint '.../{id}' (GET) called - id {}", id);

        var data = getService().findById(id);

        log.info("Endpoint '.../{id}' (GET) finished - id {}", id);

        if (data == null) {
            return new ResponseEntity<>(RestResponse.empty(404, "User not found"), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates Patient", response = PatientResult.class)
    public ResponseEntity<RestResponse<PatientResult>> update(@NotNull @PathVariable UUID id, @NotNull @RequestBody CreatePatientRequest request) {
        log.info("Endpoint '.../{id}' (PUT) called - id {}, patient {}", id, request);

        var data = getService().update(id, request);

        log.info("Endpoint '.../{id}' (PUT) finished - id {}, patient {}", id, request);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }
}
