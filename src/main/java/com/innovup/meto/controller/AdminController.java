package com.innovup.meto.controller;

import com.innovup.meto.core.web.RestResponse;
import com.innovup.meto.entity.User;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admins")
@Api(value = "Administrator API Controller", tags = "Admin API")
public class AdminController extends UsersController<AdminService> {

    protected AdminController(AdminService service) {
        super(service);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Updates Patient", response = User.class)
    public ResponseEntity<RestResponse<User>> update(@NotNull @PathVariable UUID id, @NotNull @RequestBody CreateAdminRequest request) {
        log.info("Endpoint '.../{id}' (PUT) called - id {}, admin {}", id, request);

        var data = getService().update(id, request);

        log.info("Endpoint '.../{id}' (PUT) finished - id {}, admin {}", id, request);

        return ResponseEntity.ok(RestResponse.of(data, 200));
    }
}
