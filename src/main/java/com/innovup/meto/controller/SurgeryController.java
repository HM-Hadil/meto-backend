package com.innovup.meto.controller;

import com.innovup.meto.controller.api.SurgeryApi;
import com.innovup.meto.entity.Surgery;
import com.innovup.meto.request.SurgeryRequest;
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
    public ResponseEntity<List<Surgery>> findAllSurgeries() {
        return new ResponseEntity<>(surgeryService.findAllSurgeries(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Surgery> findSurgeryById(@PathVariable UUID id) throws Exception {
        return new ResponseEntity<>(
                surgeryService.findSurgeryById(id).orElseThrow(Exception::new), // @ControllerExceptionHandler will need to be implemented, Exceptions need to be handled
                HttpStatus.OK
        );
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Surgery> addSurgery(@RequestBody SurgeryRequest request) {
        return new ResponseEntity<>(surgeryService.addSurgery(request), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Surgery> updateSurgery(@PathVariable UUID id, @RequestBody SurgeryRequest request) {
        return new ResponseEntity<>(surgeryService.updateSurgery(id, request), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Surgery> deleteSurgery(@PathVariable UUID id) {
        surgeryService.deleteSurgery(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
