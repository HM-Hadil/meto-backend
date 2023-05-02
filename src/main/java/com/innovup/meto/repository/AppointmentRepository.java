package com.innovup.meto.repository;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository  extends JpaRepository<Appointment, UUID> {

    Optional<List<Appointment>> findByDoctorIdAndStatusOrderByCreatedOn(UUID doctorId, AppointmentStatus status);
    Optional<List<Appointment>> findAllByDoctorIdAndStatusOrderByCreatedOn(UUID doctorId, AppointmentStatus status);

    Optional<List<Appointment>> findAppointmentByPatientIdOrderByCreatedOn(UUID patientId);

    List<Appointment> findByDoctorIsNullAndStatus(AppointmentStatus status);
    Optional<Appointment> findByIdAndStatusIn(UUID appointmentId, List<AppointmentStatus> statuses);

   /** @Query("SELECT a.surgery.id FROM Appointment a GROUP BY a.surgery.id ORDER BY COUNT(a) DESC")
    String findMostFrequentSurgeryId();**/

   @Query(value = "SELECT s.name, s.image ,COUNT(*) as surgery_count " +
           "FROM appointments a " +
           "JOIN chirurgie s ON a.surgery_id = s.id " +
           "GROUP BY a.surgery_id, s.name, s.image " + // Include image in GROUP BY clause
           "ORDER BY surgery_count DESC " + // Order by COUNT(*) instead of surgery_count
           "LIMIT 1", nativeQuery = true)
   List<Object[]> findMostFrequentSurgeryWithNameAndImageAndCount();

    @Query(value = "SELECT u.first_name,u.last_name,u.specialite, u.image ,COUNT(*) as doctor_count " +
            "FROM appointments a " +
            "JOIN users u ON a.doctor_i= u.id " +
            "GROUP BY a.doctor_i,u.first_name,u.specialite,u.last_name, u.image " +
            "ORDER BY doctor_count DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findMostFrequentUserWithNameAndImageAndCount();

    @Query(value = "SELECT ville, COUNT(*) as city_count "
            + "FROM appointments "
            + "GROUP BY ville "
            + "ORDER BY city_count DESC "
            + "LIMIT 1", nativeQuery = true)
    List<Object[]>getMostFrequentCity();


    @Query("SELECT COUNT(a) AS total, " +
            "SUM(CASE WHEN a.patient.gender = 'M' THEN 1 ELSE 0 END) AS males, " +
            "SUM(CASE WHEN a.patient.gender = 'F' THEN 1 ELSE 0 END) AS females " +
            "FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId " +
            "GROUP BY a.doctor.id")
    Tuple findAppointmentStatsByDoctorId(@Param("doctorId") UUID doctorId);


    @Query(value = "SELECT DATE_TRUNC('month', a.created_on) AS month, DATE_TRUNC('year', a.created_on) AS year, COUNT(*) AS appointment_count "
            + "FROM appointments a "
            + "GROUP BY month, year "
            + "ORDER BY year, month", nativeQuery = true)
    List<Object[]> getAppointmentsCountByMonthAndYear();


    @Query(value="SELECT COUNT(*), to_char(cast(cast(a.date_rdv as TIMESTAMP) as DATE), 'MM-YYYY') as YEAR, to_char(cast(cast(a.date_rdv as TIMESTAMP) as DATE), 'YYYY') as DATEFORMATTED FROM appointments a WHERE doctor_i = :doctorId group by YEAR , DATEFORMATTED order by DATEFORMATTED asc", nativeQuery=true)

    List<Object[]> getNumberAppointmentsByMonthAndDoctorId(@Param("doctorId") UUID doctorId);

    @Query(value = "SELECT a.doctor_i, COUNT(*) as doctor_count " +
            "FROM appointments a " +
            "GROUP BY a.doctor_i " +
            "ORDER BY doctor_count DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findMostFrequentDoctorId();
}

