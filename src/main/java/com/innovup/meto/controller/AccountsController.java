package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.request.PasswordChangeRequest;
import com.innovup.meto.service.AccountsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/AllActivePatient")
    public List<User> getAllActivePatient() {
        return accountsService.findAllActivatePatient();
    }

    @GetMapping("/AllActiveDoctor")
    @ApiOperation(value = "search Activate doctor", response = User.class)
    public List<User> getAllActiveDoctor(@RequestParam(required = false) String searchKeyWord  ) {
        return accountsService.findAllActiveDoctor(searchKeyWord);
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
    @GetMapping("/ActivePatient/{id}")
    public ResponseEntity<User> getPatientByIdWithRoleAndEnabledTrue(@PathVariable UUID id) throws Exception {
        var patient = accountsService.getPatientByIdWithRoleAndEnabledTrue(id).orElseThrow(Exception::new);
        return new ResponseEntity<>(patient,  HttpStatus.OK);
    }
    //delete users
    @DeleteMapping("/deleteAccount/{id}")
    public void deleteUser(@PathVariable UUID id){
    accountsService.DeleteAccounts(id);
 }

 //activate accounts
    @PutMapping("/activateAccount/{id}")
    @ApiOperation(value = "Activate User", response = User.class)
    public User activate(@PathVariable UUID id){
        log.info("Endpoint '.../{id}' (PUT) called - id {}, account {}", id);

        return accountsService.Activate(id);

    }

    @PutMapping("/desactivateAccount/{id}")
    @ApiOperation(value = "Activate User", response = User.class)
    public User desactivate(@PathVariable UUID id){
        log.info("Endpoint '.../{id}' (PUT) called - id {}, account {}", id);

        return accountsService.Desactivate(id);

    }



    @GetMapping("/search")
    @ApiOperation(value = "search doctor by name ", response = User.class)
    public ResponseEntity<List<User>> searchProducts(@RequestParam("query") String query){
        return ResponseEntity.ok(accountsService.searchDoctor(query));
    }


    @GetMapping("/count-patients-per-gender")
    public ResponseEntity<List<Object[]>> getCountPatientPerGender() {
        List<Object[]> result = accountsService.countPatientPerGender();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/users/{email}/password")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody PasswordChangeRequest request) {
        try {
            accountsService.changePassword(email, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password changed successfully.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/doctor-count")
    public int getDoctorCount() {
        return accountsService.getDoctorCount();
    }
    @GetMapping("/patient-count")
    public int getPatientCount() {
        return accountsService.getPatientCount();
    }
}

