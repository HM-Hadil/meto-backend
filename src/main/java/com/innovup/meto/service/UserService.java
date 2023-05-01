package com.innovup.meto.service;

import com.innovup.meto.entity.ConfirmationToken;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.result.UserResult;

import java.util.UUID;

public abstract class UserService<RESULT extends UserResult> {

    private final Role role;
    private final UserRepository userRepository;

    protected UserService(Role role, UserRepository repository) {
        this.role = role;
        this.userRepository = repository;
    }
}
