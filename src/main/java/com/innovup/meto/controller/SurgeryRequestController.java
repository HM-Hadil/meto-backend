package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.service.SurgeriesRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/surgeryRequests")
@RequiredArgsConstructor
@Api(value = "Surgery requests API Controller", tags = "Surgery Requests API")
public class SurgeryRequestController {

    private final SurgeriesRequestRepository surgeriesRequestRepository;
    private final SurgeriesRequestService surgeriesRequestService;


    @GetMapping("")
    @ApiOperation(value = "Finds All Surgery requests", response = SurgeriesRequest.class, tags = {"Surgery Requests API"})
    public ResponseEntity<RestResponse<List<SurgeriesRequest>>> findAllRequestedSurgeries() {

        log.info("Endpoint '/surgeries/requested' (GET) called");
        var data = surgeriesRequestService.findAllRequestedSurgeries();
        log.info("Endpoint '/surgeries/requested' (GET) finished");
        return new ResponseEntity<>(RestResponse.of(data,  200), HttpStatus.OK);
    }

    @PostMapping(value = "/{doctorId}/request")
    @ApiOperation(
            value = "Create a surgery request by the doctor who requested it, specifying doctorId",
            response = SurgeriesRequest.class, tags = {"Surgery Requests API"}
    )
    public ResponseEntity<RestResponse<SurgeriesRequest>> requestSurgery(
            @NotNull @PathVariable UUID doctorId,
            @NotNull @RequestBody SurgeryRequest request
    ) {
        log.info("Endpoint '/surgeryRequests/{doctorsId}' (POST) called - doctorId {}, surgeryRequest {}", doctorId, request);
        var data = surgeriesRequestService.createSurgeryRequest(doctorId, request);
        log.info("Endpoint '/surgeryRequests/{doctorsId}' (POST) finished - id {}, surgeryRequest {}", doctorId, request);
        return ResponseEntity.ok(RestResponse.of(data, 201));
    }

    @PutMapping(value = "/{surgeryId}/approve")
    @ApiOperation(value = "Approve a surgery request with the specified surgeryId", response = SurgeryResult.class)
    public ResponseEntity<RestResponse<SurgeryResult>> approveSurgery(@NotNull @PathVariable UUID surgeryId) {
        log.info("Endpoint '.../{surgeryId}' (PUT) called - id {}", surgeryId);
        var data = surgeriesRequestService.approveSurgeryRequest(surgeryId);
        log.info("Endpoint '.../{surgeryId}' (PUT) finished - id {}", surgeryId);
        return ResponseEntity.ok(RestResponse.of(data, 200));
    }

    @DeleteMapping(value = "/{surgeryId}")
    @ApiOperation(value = "Delete a surgery request", response = SurgeriesRequest.class, tags = {"Surgery Requests API"})
    public ResponseEntity<RestResponse<List<DoctorResult>>> testDelete(@PathVariable UUID surgeryId) {
        surgeriesRequestRepository.deleteById(surgeryId);
        return ResponseEntity.ok(RestResponse.empty(200, "deleted id = " + surgeryId));
    }
}
