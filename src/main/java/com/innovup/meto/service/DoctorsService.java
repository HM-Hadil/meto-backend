package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.result.DoctorResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public  class DoctorsService extends UserService<DoctorResult> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ChirurgieRequestService surgeriesRequestService;

    protected DoctorsService(UserRepository repository, UserMapper userMapper,ChirurgieRequestService service) {
        super(Role.DOCTOR, repository);
        this.userRepository = repository;
        this.userMapper = userMapper;
        surgeriesRequestService = service;
    }

    public List<DoctorResult> findAll() {
        return userRepository.findAllByRole(Role.DOCTOR).stream()
                .map(userMapper::entityToDoctor)
                .toList();
    }

    public List<DoctorResult>  findAllDoctorsBySurgeryId(UUID surgeryId) {
        return userRepository.findAllBySpecialitesId(surgeryId).stream()
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
            user.setImage(request.getImage());
            user.setTelephone(request.getTelephone());
            user.setSpecialite(request.getSpecialite());
            user.setGender(request.getGender());
            user.setAdresse(request.getAdresse());
            user.setVille(request.getVille());
            user.setParcours(request.getParcours());
            user.setExperience(request.getExperience());
            return userMapper.entityToDoctor(userRepository.save(user));
        } else {
            throw new UserNotFoundException(Role.DOCTOR);
        }
    }

    public DoctorResult updatePhoto(UUID id, CreateDoctorRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setId(id);
            user.setImage(request.getImage());

            return userMapper.entityToDoctor(userRepository.save(user));
        } else {
            throw new UserNotFoundException(Role.DOCTOR);
        }
    }



    public com.innovup.meto.entity.ChirurgieRequest createSurgeryRequest(UUID doctorId, ChirurgieRequest req){
        return surgeriesRequestService.createSurgeryRequest(doctorId, req);
    }


}