package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public abstract  class UserService {
    private final Role role;
    private final UserRepository userRepository;

    protected UserService(Role role, UserRepository repository) {
        this.role = role;
        this.userRepository = repository;
    }

    public List<User> findAll() {
        return userRepository.findAllByRole(role);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
