package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.service.SurgeriesRequestService;
import com.innovup.meto.service.SurgeryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/surgeries")
@Api(value = "Surgery API Controller", tags = "Surgery API")
public class SurgeryController {

    private final SurgeryService surgeryService;

    @GetMapping("")
    @ApiOperation(value = "Finds All Surgeries", response = SurgeryResult.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<List<SurgeryResult>>> findAllSurgeries() {

        log.info("Endpoint '/surgeries' (GET) called");

        var data = surgeryService.findAllSurgeries();

        return new ResponseEntity<>(RestResponse.of(data,  200), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a Surgery by id", response = SurgeryResult.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<SurgeryResult>> findSurgeryById(@PathVariable UUID id) throws Exception {

        log.info("Endpoint '/surgeries/{id}' (GET) called - id = {}", id);

        // @ControllerExceptionHandler will need to be implemented, Exceptions need to be handled
        var data = surgeryService.findSurgeryById(id).orElseThrow(Exception::new);

        return new ResponseEntity<>(RestResponse.of(data,  200), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "create Surgery", response = SurgeryResult.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<SurgeryResult>> addSurgery(@RequestBody SurgeryRequest request) {

        log.info("Endpoint '/surgeries' (POST) called - request = {}", request);

        var data = surgeryService.addSurgery(request);

        return new ResponseEntity<>(RestResponse.of(data,  201), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update Surgery", response = SurgeryResult.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<SurgeryResult>> updateSurgery(@PathVariable UUID id, @RequestBody SurgeryRequest request) {

        log.info("Endpoint '/surgeries/{id}' (PUT) called - id = {} - request = {}", id, request);

        var data = surgeryService.updateSurgery(id, request);

        return new ResponseEntity<>(RestResponse.of(data,  200), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Surgery by id", response = SurgeryResult.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<Void>> deleteSurgery(@PathVariable UUID id) {

        log.info("Endpoint '/surgeries/{id}' (DELETE) called - id = {}", id);

        surgeryService.deleteSurgery(id);

        return new ResponseEntity<>(RestResponse.empty(203, null), HttpStatus.OK);
    }
}
