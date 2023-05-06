package com.innovup.meto.controller;

import com.innovup.meto.entity.PatientOpinion;
import com.innovup.meto.request.OpinionRequest;
import com.innovup.meto.result.OpinionResult;
import com.innovup.meto.service.OpinionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/opinion/")
@Api(value = "Opinion API Controller", tags = "Opinion API")
public class OpinionController {
    private final OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @PostMapping(path ="createOpinion", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new opinion", response = OpinionResult.class, tags = {"opinion API"})
    public ResponseEntity<OpinionResult> createOpinion(@RequestBody OpinionRequest request) {
        log.info("Endpoint '/opinion' (POST) called - request {}", request);

        var response = opinionService.createPatientOpinion(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    @ApiOperation(value = "Find All opinion", response = OpinionResult.class, tags = {"opinion API"})
    public ResponseEntity<List<OpinionResult>> findAllOpinions() {
        log.info("Endpoint '/opinions' (GET) called");

        var data = opinionService.getAllPatientOpinionAndIsEnabledFalse();

        return ResponseEntity.ok(data);
    }
    @GetMapping("getAllOpinionTrue")
    @ApiOperation(value = "Find All accepted opinion ", response = OpinionResult.class, tags = {"opinion API"})
    public ResponseEntity<List<OpinionResult>> findAllEnabledOpinion() {
        log.info("Endpoint '/opinions' (GET) called");

        var data = opinionService.getAllPatientOpinionAndIsEnabledTrue();

        return ResponseEntity.ok(data);
    }

    @PutMapping("/accepterAvis/{id}")
    @ApiOperation(value = "accepter avis", response = PatientOpinion.class)

    public PatientOpinion accepterAvis(@PathVariable UUID id)  {
        return   opinionService.accepter(id);

    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "delete avis", response = PatientOpinion.class, tags = {"opinion API"})

    public void delete(@PathVariable UUID id)  {
           opinionService.delete(id);
    }
}
