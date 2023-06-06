package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.result.DoctorResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/surgeryRequests")
@RequiredArgsConstructor
@Api(value = "Surgery requests API Controller", tags = "Surgery Requests API")
public class SurgeryRequestController {

    private final SurgeriesRequestRepository surgeriesRequestRepository;

    @DeleteMapping(value = "/testDelete/{id}")
    @ApiOperation(value = "delete", response = SurgeriesRequest.class)
    public ResponseEntity<RestResponse<List<DoctorResult>>> testDelete(@PathVariable UUID id) {
        surgeriesRequestRepository.deleteById(id);
        return ResponseEntity.ok(RestResponse.empty(200, "deleted id = " + id));
    }
}
