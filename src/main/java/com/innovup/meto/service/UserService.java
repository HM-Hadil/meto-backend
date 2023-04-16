package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateUserRequest;
import com.innovup.meto.result.UserResult;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class UserService<RESULT extends UserResult> {

    private final Role role;
    private final UserRepository userRepository;

    protected UserService(Role role, UserRepository repository) {
        this.role = role;
        this.userRepository = repository;
    }





}
