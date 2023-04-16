package com.innovup.meto.repository;

import com.innovup.meto.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository  extends JpaRepository<Appointment, UUID> {

    Optional<List<Appointment>> findAppointmentByDoctorIdOrderByCreatedOn(UUID doctorId);
Optional<List<Appointment>> findAppointmentByPatientIdOrderByCreatedOn(UUID patientId);

    List<Appointment> findByDoctorIsNull();
}