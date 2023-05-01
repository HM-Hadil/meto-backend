package com.innovup.meto.service;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.ConfirmationToken;
import com.innovup.meto.entity.Experience;
import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.ConfirmationTokenRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.*;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.result.PatientResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final SurgeryService surgeryService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public AdministratorResult createNewAdmin(CreateAdminRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withGender(request.getGender())
                .withLastname(request.getLastname())
                .withRole(Role.ADMIN)
                .withIsEnabled(false)
                .withCreatedOn(LocalDate.now())
                .build();
        user = userRepository.save(user);
        sendConfirmationToken(user.getEmail());
         return userMapper.entityToAdministrator(user);
    }

    public PatientResult createNewPatient(CreatePatientRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withGender(request.getGender())
                .withRole(Role.PATIENT)
                .withIsEnabled(false)
                .withCreatedOn(LocalDate.now())
                .build();
        user = userRepository.save(user);
        sendConfirmationToken(user.getEmail());
        return userMapper.entityToPatient(user);
    }

    public DoctorResult createNewDoctor(CreateDoctorRequest request) {
        var surgeries = surgeryService.findSurgeriesByIds(request.getSurgeries());

        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withCity(request.getCity())
                .withGender(request.getGender())
                .withAcademicLevels(createAcademicLevels(request.getAcademicLevels()))
                .withExperiences(createExperiences(request.getExperiences()))
                .withSpecialties(surgeries)
                .withAddress(request.getAddress())
                .withRole(Role.DOCTOR)
                .withIsEnabled(false)
                .withCreatedOn(LocalDate.now())
                .build();
        user = userRepository.save(user);
        sendConfirmationToken(user.getEmail());
        return userMapper.entityToDoctor(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public ConfirmationToken createConfirmationToken(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        var token = UUID.randomUUID().toString();
        var confirmationToken = ConfirmationToken.builder()
                .withId(UUID.randomUUID())
                .withToken(token)
                .withUser(user)
                .build();
        return confirmationTokenRepository.save(confirmationToken);
    }

    private void sendConfirmationToken(String email) {
        var confirmationToken = createConfirmationToken(email);
        mailService.send(confirmationToken);
    }

    private List<AcademicLevel> createAcademicLevels(List<AcademicLevelRequest> academicLevelsRequest) {
        if (academicLevelsRequest == null) {
            return Collections.emptyList();
        }
        return academicLevelsRequest.stream()
                .map(this::newAcademicLevel)
                .toList();
    }

    private AcademicLevel newAcademicLevel(AcademicLevelRequest request) {
        return AcademicLevel.builder()
                .withId(UUID.randomUUID())
                .withField(request.getField())
                .withDiploma(request.getDiploma())
                .withEstablishment(request.getEstablishment())
                .build();
    }

    private List<Experience> createExperiences(List<ExperienceRequest> experiencesRequest) {
        if (experiencesRequest == null) {
            return Collections.emptyList();
        }
        return experiencesRequest.stream()
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
