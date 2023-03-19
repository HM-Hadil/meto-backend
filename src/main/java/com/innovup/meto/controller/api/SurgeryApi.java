package com.innovup.meto.controller.api;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.request.SurgeryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Api(value = "Surgery API Controller", tags = "Surgery API")
public interface SurgeryApi {

    @ApiOperation(value = "Finds All Surgeries", notes = "This operation will fetch all surgeries", response = Surgery.class, tags = {"Surgery API"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fetched", response = Surgery.class),})
    ResponseEntity<List<Surgery>> findAllSurgeries();

    @ApiOperation(value = "Find a Surgery by id", notes = "This operation will try fetch a surgery by id", response = Surgery.class, tags = {"Surgery API"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Fetched", response = Surgery.class),})
    ResponseEntity<Surgery> findSurgeryById(UUID id) throws Exception;

    @ApiOperation(value = "create Surgery", notes = "This operation will create a new surgery", response = Surgery.class, tags = {"Surgery API"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = Surgery.class),})
    ResponseEntity<Surgery> addSurgery(SurgeryRequest request);

    @ApiOperation(value = "Update Surgery", notes = "This operation will update an existing surgery", response = Surgery.class, tags = {"Surgery API"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Updated", response = Surgery.class),})
    ResponseEntity<Surgery> updateSurgery(UUID id, SurgeryRequest request);

    @ApiOperation(value = "Delete Surgery by id", notes = "This operation will delete a surgery by id", response = Surgery.class, tags = {"Surgery API"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Deleted", response = Surgery.class),})
    ResponseEntity<Surgery> deleteSurgery(UUID id);

}
