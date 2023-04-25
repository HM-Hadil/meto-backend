package com.innovup.meto.mapper;

import com.innovup.meto.entity.User;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.result.PatientResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class UserMapper {

    private final ChirurgieMapper surgeryMapper;

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
                .withGender(user.getGender())
               // .withWeight(user.getWeight())
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
                .withGender(user.getGender())
                .withAddress(user.getAdresse())
                .withCity(user.getVille())
                .withParcours((user.getParcours()))
                .withExperiences(user.getExperience())
                .withImage(user.getImage())
                .withSpecialite(user.getSpecialite())
                .withTelephone(user.getTelephone())

                .withSurgeries(
                        user.getSpecialites().stream()
                                .map(
                                       surgeryMapper ::entityToName
                                )
                                .toList()
                )
                .build();
    }
}
