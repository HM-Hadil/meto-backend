package com.innovup.meto.service;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.Experience;
import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User createNewAdmin(CreateAdminRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withLastname(request.getLastname())
                .withRole(Role.ADMIN)
                .withIsEnabled(true)
                .withCreatedOn(LocalDate.now())
                .build();

        // ObjectMapper.map(user, createUserResult)

        return userRepository.save(user);
    }

    public User createNewPatient(CreatePatientRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(Role.PATIENT)
                .withCreatedOn(LocalDate.now())
                .build();
        return userRepository.save(user);
    }


    public User createNewDoctor(CreateDoctorRequest request) {

        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withVille(request.getVille())
                .withParcours(createAcademicLevels(request.getParcours()))
                .withGender(request.getGender())
                .withExperience(createExperiences(request.getExperience()))
                .withAdresse(request.getAdresse())
                .withRole(Role.DOCTOR)
                .withCreatedOn(LocalDate.now())
                .build();
        return userRepository.save(user);
    }

    public User findUserById(UUID uuid) {
        Optional<User> optional = userRepository.findById(uuid);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("User with id {} not found" + uuid);
    }

    private List<AcademicLevel> createAcademicLevels(List<ParcoursRequest> academicLevelRequests) {
        return academicLevelRequests.stream()
                .map(this::newAcademicLevel)
                .toList();
    }

    private AcademicLevel newAcademicLevel(ParcoursRequest request) {
        return AcademicLevel.builder()
                .withId(UUID.randomUUID())
                .withField(request.getField())
                .withDiploma(request.getDiploma())
                .withEstablishment(request.getEstablishment())
                .build();
    }

    private List<Experience> createExperiences(List<ExperienceRequest> experienceRequests) {
        return experienceRequests.stream()
                .map(this::newExperience)
                .toList();
    }

    private Experience newExperience(ExperienceRequest request) {
        return Experience.builder()
                .withId(UUID.randomUUID())
                .withSpecialty(request.getSpecialty())
                .withEstablishment(request.getEstablishment())
                .build();
    }


}
