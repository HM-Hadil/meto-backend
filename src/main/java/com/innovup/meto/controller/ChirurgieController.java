package com.innovup.meto.controller;


import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.Chirurgie;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.result.ChirurgieResult;
import com.innovup.meto.service.ChirurgieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin( origins = {"http://localhost:4200"})
@RequestMapping("/chirurgies/")
@Slf4j
@RequiredArgsConstructor
@RestController
@Api(value = "Surgery API Controller", tags = "Surgery API")
public class ChirurgieController  {
    private final ChirurgieService surgeryService;

    @GetMapping("getAllChirurgie")
    @ApiOperation(value = "Finds All Surgeries", response = Chirurgie.class, tags = {"Surgery API"})
    public ResponseEntity<List<ChirurgieResult>> findAllSurgeries() {

        log.info("Endpoint '/surgeries' (GET) called");

        var data = surgeryService.findAllSurgeries();

        return new ResponseEntity<>( data,HttpStatus.OK);
    }

    @GetMapping("GetChirurgieById/{id}")
    @ApiOperation(value = "Find a Surgery by id", response = Chirurgie.class, tags = {"Surgery API"})
    public ResponseEntity<ChirurgieResult> findSurgeryById(@PathVariable UUID id) throws Exception {

        log.info("Endpoint '/surgeries/{id}' (GET) called - id = {}", id);

        // @ControllerExceptionHandler will need to be implemented, Exceptions need to be handled
        var data = surgeryService.findSurgeryById(id).orElseThrow(Exception::new);

        return new ResponseEntity<>(data,  HttpStatus.OK);
    }

    @PostMapping("addChirurgie")
    @ApiOperation(value = "create Surgery", response = Chirurgie.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<ChirurgieResult>> addSurgery(@RequestBody ChirurgieRequest request) {

        log.info("Endpoint '/surgeries' (POST) called - request = {}", request);

        var data = surgeryService.addSurgery(request);

        return new ResponseEntity<>(RestResponse.of(data,  201), HttpStatus.CREATED);
    }

    @PutMapping("UpdateChirurgie/{id}")
    @ApiOperation(value = "Update Surgery", response = Chirurgie.class, tags = {"Surgery API"})
    public ResponseEntity<ChirurgieResult> updateSurgery(@PathVariable UUID id, @RequestBody ChirurgieRequest request) {

        log.info("Endpoint '/surgeries/{id}' (PUT) called - id = {} - request = {}", id, request);

        var data = surgeryService.updateSurgery(id, request);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("DeleteChirurgie/{id}")
    @ApiOperation(value = "Delete Surgery by id", response = Chirurgie.class, tags = {"Surgery API"})
    public ResponseEntity<RestResponse<Chirurgie>> deleteSurgery(@PathVariable UUID id) {

        log.info("Endpoint '/surgeries/{id}' (DELETE) called - id = {}", id);

        surgeryService.deleteSurgery(id);

        return new ResponseEntity<>(RestResponse.empty(203, null), HttpStatus.OK);
    }

}