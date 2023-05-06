package com.innovup.meto.repository;

import com.innovup.meto.entity.PatientOpinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PatientOpinionRepo extends JpaRepository<PatientOpinion, UUID> {
  List<PatientOpinion> findByIsEnabledFalse();
  List<PatientOpinion> findByIsEnabledTrue();
}
