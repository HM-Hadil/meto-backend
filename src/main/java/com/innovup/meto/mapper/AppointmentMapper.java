package com.innovup.meto.mapper;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.result.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class AppointmentMapper {

    private final SurgeryMapper surgeryMapper;
    private final UserMapper userMapper;

    public AppointmentResult entityToResult(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return AppointmentResult.builder()
                .withId(appointment.getId())
                .withNote(appointment.getNote())
                .withStatus(appointment.getStatus())
                .withCreatedOn(appointment.getCreatedOn())
                .withSurgery(surgeryMapper.entityToResult(appointment.getSurgery()))
                .withPatient(userMapper.entityToPatient(appointment.getPatient()))
                .withDoctor(userMapper.entityToDoctor(appointment.getDoctor()))
                .withAdministrator(userMapper.entityToAdministrator(appointment.getAdministrator()))
                .withRendezVous(
                        RendezVousResult.builder()
                                .withId(appointment.getRendezVous().getId())
                                .withStatus(appointment.getRendezVous().getStatus())
                                .withDate(appointment.getRendezVous().getDate())
                                .withCreatedOn(appointment.getRendezVous().getCreatedOn())
                                .withLastUpdatedOn(appointment.getRendezVous().getLastUpdatedOn())
                                .build()
                )
                .build();
    }
}
