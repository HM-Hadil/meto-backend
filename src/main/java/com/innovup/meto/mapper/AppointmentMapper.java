package com.innovup.meto.mapper;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.result.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppointmentMapper {

    private final SurgeryMapper surgeryMapper;
    private final UserMapper userMapper;

    private final RendezVousMapper rendezVousMapper;

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
                .withRendezVous(rendezVousMapper.entityToResult(appointment.getRendezVous()))
                .build();
    }
}
