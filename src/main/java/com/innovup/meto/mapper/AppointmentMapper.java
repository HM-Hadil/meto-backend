package com.innovup.meto.mapper;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.pojo.Administrator;
import com.innovup.meto.result.AppointmentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentMapper {


    private final UserMapper userMapper;
    private final RendezVousMapper rendezVousMapper;

    public AppointmentResult entityToResult(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return AppointmentResult.builder()
                .withId(appointment.getId())
                .withNote(appointment.getDescription())
                .withStatus(appointment.getStatus())
                .withAge(appointment.getAge())
                .withDateRDV(appointment.getDateRDV())
                .withWeight(appointment.getWeight())
                .withPhone(appointment.getPhone())
                .withImage(appointment.getImage())
                .withVille(appointment.getVille())
                .withTypeSang(appointment.getTypeSang())
                .withCreatedOn(appointment.getCreatedOn())

                .withLastUpdatedBy(
                        appointment.getLastUpdatedBy() != null ?
                                Administrator.builder()
                                        .withFirstname(appointment.getLastUpdatedBy().getFirstname())
                                        .withLastname(appointment.getLastUpdatedBy().getLastname())
                                        .withEmail(appointment.getLastUpdatedBy().getEmail())
                                        .build()
                                : null
                )

                .withLastUpdatedOn(appointment.getLastUpdatedOn() != null ? appointment.getLastUpdatedOn() : null)

                .withSurgery(ChirurgieMapper.entityToResult(appointment.getSurgery()))
                .withPatient(userMapper.entityToPatient(appointment.getPatient()))
                .withDoctor(userMapper.entityToDoctor(appointment.getDoctor()))
                .withAdministrator(userMapper.entityToAdministrator(appointment.getAdministrator()))
                .withRendezVous(rendezVousMapper.entityToResult(appointment.getRendezVous()))

                .build();
    }
}
