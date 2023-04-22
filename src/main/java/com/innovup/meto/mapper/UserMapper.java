package com.innovup.meto.mapper;

import com.innovup.meto.entity.User;
import com.innovup.meto.result.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class UserMapper {

    private final SurgeryMapper surgeryMapper;

    public AdministratorResult entityToAdministrator(User user) {
        if (user == null) {
            return null;
        }

        return AdministratorResult.builder()
                .withId(user.getId())
                .withFirstname(user.getFirstname())
                .withLastname(user.getLastname())
                .withEmail(user.getLastname())
                .withRole(user.getRole())
                .withCreatedOn(user.getCreatedOn())
                .build();
    }

    public PatientResult entityToPatient(User user) {
        if (user == null) {
            return null;
        }

        return PatientResult.builder()
                .withId(user.getId())
                .withFirstname(user.getFirstname())
                .withLastname(user.getLastname())
                .withEmail(user.getEmail())
                .withRole(user.getRole())
                .withCreatedOn(user.getCreatedOn())
                .withGender(user.getGender())
                .withWeight(user.getWeight())
                .build();
    }

    public DoctorResult entityToDoctor(User user) {
        if (user == null) {
            return null;
        }

        return DoctorResult.builder()
                .withId(user.getId())
                .withFirstname(user.getFirstname())
                .withLastname(user.getLastname())
                .withEmail(user.getEmail())
                .withRole(user.getRole())
                .withCreatedOn(user.getCreatedOn())
                .withGender(user.getGender())
                .withAddress(user.getAddress())
                .withCity(user.getCity())
                .withAcademicLevels(user.getAcademicLevels())
                .withExperiences(user.getExperiences())
                .withSurgeries(
                        user.getSurgeries().stream()
                                .map(
                                        surgeryMapper::entityToName
                                )
                                .toList()
                )
                .build();
    }
}
