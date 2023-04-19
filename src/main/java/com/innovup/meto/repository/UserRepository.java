package com.innovup.meto.repository;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByRole(Role role);

    Optional<User> findByEmail(String email);

    List<User> findByRoleAndIsEnabledTrue(Role role);

    List<User> findByRoleAndIsEnabled(Role role, boolean isEnabled);

    Optional<User> findByIdAndRoleAndIsEnabledFalse(UUID id, Role role);

    Optional<User> findByIdAndRoleAndIsEnabledTrue(UUID id, Role role);

    List<User> findAllBySurgeriesId(UUID surgeryId);


    @Query("SELECT u FROM User u WHERE u.role = 'DOCTOR' AND (u.specialite LIKE CONCAT('%',:query, '%') OR u.firstname LIKE CONCAT('%', :query, '%'))")
    List<User> searchDoctor(@Param("query") String query);

    @Query(value = "SELECT gender, count(*) FROM User u WHERE u.role = 'D' GROUP BY u.gender")
    List<Object[]> countPatientPerGender();

    @Query(value =  "SELECT COUNT(*) as doctor_count FROM User u WHERE u.role = 'D'")
    public int getDoctorCount();

    @Query(value =  "SELECT COUNT(*) as doctor_count FROM User u WHERE u.role = 'P'")
    public int getPatientCount();
}