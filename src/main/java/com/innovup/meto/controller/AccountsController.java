package com.innovup.meto.controller;

import com.innovup.meto.entity.User;
import com.innovup.meto.service.AccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @GetMapping("/disabledDoctor")
    public List<User> getDisabledDoctor() {
        return accountsService.findDisabledDoctor();
    }


    @GetMapping("/doctor/{id}")
    public ResponseEntity<User> getUserByIdWithRoleAndEnabledFalse(@PathVariable UUID id) throws Exception {
    var doctor = accountsService.getUserByIdWithRoleAndEnabledFalse(id).orElseThrow(Exception::new);
       return new ResponseEntity<>(doctor,  HttpStatus.OK);
    }

}
