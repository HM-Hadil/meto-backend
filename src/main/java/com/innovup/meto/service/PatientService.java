package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.PatientResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService extends UserService<PatientResult> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    protected PatientService(UserRepository repository, UserMapper userMapper) {
        super(Role.PATIENT, repository);
        this.userRepository = repository;
        this.userMapper = userMapper;
    }

    public List<PatientResult> findAll() {
        return userRepository.findAllByRole(Role.PATIENT).stream()
                .map(userMapper::entityToPatient)
                .toList();
    }

    public PatientResult findById(UUID id) {
        return userMapper.entityToPatient(userRepository.findById(id).orElse(null));
    }

    public PatientResult update(UUID id, CreatePatientRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            return userMapper.entityToPatient(userRepository.save(user));
        } else {
            throw new RuntimeException();
        }
    }
}
