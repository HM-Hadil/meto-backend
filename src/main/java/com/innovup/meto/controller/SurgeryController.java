package com.innovup.meto.controller;

import com.innovup.meto.controller.api.SurgeryApi;
import com.innovup.meto.entity.Surgery;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.service.SurgeryService;
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
public class SurgeryController implements SurgeryApi {

    private final SurgeryService surgeryService;


    @Override
    @GetMapping("")
    public ResponseEntity<List<SurgeryResult>> findAllSurgeries() {
        log.info("Endpoint '/surgeries' (GET) called");
        var response = surgeryService.findAllSurgeries();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SurgeryResult> findSurgeryById(@PathVariable UUID id) throws Exception {
        log.info("Endpoint '/surgeries/{id}' (GET) called - id = {}", id);
        // @ControllerExceptionHandler will need to be implemented, Exceptions need to be handled
        var response = surgeryService.findSurgeryById(id).orElseThrow(Exception::new);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<SurgeryResult> addSurgery(@RequestBody SurgeryRequest request) {
        log.info("Endpoint '/surgeries' (POST) called - request = {}", request);
        var response = surgeryService.addSurgery(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SurgeryResult> updateSurgery(@PathVariable UUID id, @RequestBody SurgeryRequest request) {
        log.info("Endpoint '/surgeries/{id}' (PUT) called - id = {} - request = {}", id, request);
        var response = surgeryService.updateSurgery(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Surgery> deleteSurgery(@PathVariable UUID id) {
        log.info("Endpoint '/surgeries/{id}' (DELETE) called - id = {}", id);
        surgeryService.deleteSurgery(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
