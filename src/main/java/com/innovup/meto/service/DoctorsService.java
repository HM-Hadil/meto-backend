package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.result.PatientResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorsService extends UserService<DoctorResult> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    protected DoctorsService(UserRepository repository, UserMapper userMapper) {
        super(Role.DOCTOR, repository);
        this.userRepository = repository;
        this.userMapper = userMapper;
    }

    public List<DoctorResult> findAll() {
        return userRepository.findAllByRole(Role.DOCTOR).stream()
                .map(userMapper::entityToDoctor)
                .toList();
    }

    public List<DoctorResult>  findAllDoctorsBySurgeryId(UUID surgeryId) {
        return userRepository.findAllBySurgeriesId(surgeryId).stream()
                .map(userMapper::entityToDoctor)
                .toList();
    }

    public DoctorResult findById(UUID id) {
        return userMapper.entityToDoctor(userRepository.findById(id).orElse(null));
    }

    public DoctorResult update(UUID id, User request) {
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
            return userMapper.entityToDoctor(userRepository.save(user));
        } else {
            throw new RuntimeException();
        }
    }
}
