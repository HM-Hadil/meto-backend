package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.service.AccountsService;
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
@RequestMapping("/accounts")
@Api(value = "Accounts Users API Controller", tags = "Accounts API")
public class AccountsController {

    private final AccountsService accountsService;

    @GetMapping("/disabledDoctor")
    public List<User> getDisabledDoctor() {
        return accountsService.findDisabledDoctor();
    }

    @GetMapping("/disabledPatient")
    public List<User> getDisabledPatient() {
        return accountsService.findDisabledPatient();
    }


    @GetMapping("/patient/{id}")
    public ResponseEntity<User> getUsersByIdWithRoleAndEnabledFalse(@PathVariable UUID id) throws Exception {
    var patient = accountsService.getUsersByIdWithRoleAndEnabledFalse(id).orElseThrow(Exception::new);
       return new ResponseEntity<>(patient,  HttpStatus.OK);
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity<User> getUserByIdWithRoleAndEnabledFalse(@PathVariable UUID id) throws Exception {
        var doctor = accountsService.getUserByIdWithRoleAndEnabledFalse(id).orElseThrow(Exception::new);
        return new ResponseEntity<>(doctor,  HttpStatus.OK);
    }
    @GetMapping("/Activedoctor/{id}")
    public ResponseEntity<User> getUserByIdWithRoleAndEnabledTrue(@PathVariable UUID id) throws Exception {
        var doctor = accountsService.getUserByIdWithRoleAndEnabledTrue(id).orElseThrow(Exception::new);
        return new ResponseEntity<>(doctor,  HttpStatus.OK);
    }

    //delete users
    @DeleteMapping("/deleteAccount/{id}")
    public void deleteUser(@PathVariable UUID id){
    accountsService.DeleteAccounts(id);
 }

 //activate accounts
    @PutMapping("/activateAccount/{id}")
    @ApiOperation(value = "Activate User", response = User.class)
    public User activateDoctor(@PathVariable UUID id){
        log.info("Endpoint '.../{id}' (PUT) called - id {}, doctor {}", id);

        return accountsService.Activate(id);

    }

}
