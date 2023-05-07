package com.innovup.meto.mapper;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.pojo.Administrator;
import com.innovup.meto.result.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentMapper {

    private final SurgeryMapper surgeryMapper;
    private final UserMapper userMapper;
    private final RendezVousMapper rendezVousMapper;
    private final DevisMapper devisMapper;

    public AppointmentResult entityToResult(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return AppointmentResult.builder()
                .withId(appointment.getId())
                .withNote(appointment.getNote())
                .withStatus(appointment.getStatus())
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
                .withSurgery(surgeryMapper.entityToResult(appointment.getSurgery()))
                .withPatient(userMapper.entityToPatient(appointment.getPatient()))
                .withDoctor(userMapper.entityToDoctor(appointment.getDoctor()))
                .withAdministrator(userMapper.entityToAdministrator(appointment.getAdministrator()))
                .withRendezVous(rendezVousMapper.entityToResult(appointment.getRendezVous()))
                .withDevis(devisMapper.entityToResult(appointment.getDevis()))
                .build();
    }
}
