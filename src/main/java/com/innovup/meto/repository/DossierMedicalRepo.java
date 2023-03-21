package com.innovup.meto.repository;

import com.innovup.meto.prendreRendezVous.entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DossierMedicalRepo extends JpaRepository<DossierMedical , UUID> {
    Optional<DossierMedical> findByNumeroDossierMedicale(UUID NumeroDossierMedicale);


    boolean existsBynumeroDossierMedicale(UUID numeroDossierMedicale);

    void deleteByNumeroDossierMedicale(UUID NumeroDossierMedicale);
}
