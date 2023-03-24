package com.innovup.meto.repository;

import com.innovup.meto.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAppointmentByDoctorOrderByCreatedOn(UUID doctorId);
}
