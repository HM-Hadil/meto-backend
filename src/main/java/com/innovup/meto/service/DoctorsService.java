package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DoctorsService extends UserService {

    private final UserRepository userRepository;

    protected DoctorsService(UserRepository repository) {
        super(Role.DOCTOR, repository);
        this.userRepository = repository;
    }

    public User update(UUID id, User request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setId(id);
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setGender(request.getGender());
            user.setAddress(request.getAddress());
            user.setCity(request.getCity());
            user.setAcademicLevels(request.getAcademicLevels());
            user.setExperiences(request.getExperiences());
            return userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }
}
