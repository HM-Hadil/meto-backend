package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreatePatientRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientService extends UserService{

    private final UserRepository userRepository;

    protected PatientService(UserRepository repository) {
        super(Role.ADMIN, repository);
        this.userRepository = repository;
    }

    public User update(UUID id, CreatePatientRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            return userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }
}
