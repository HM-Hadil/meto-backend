package com.innovup.meto.service;

import com.innovup.meto.entity.DossierMedical;
import com.innovup.meto.repository.DossierMedicalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DossierMedicalService {
    @Autowired
    private DossierMedicalRepo dossierMedicalRepo;
    //add dossierMedical
    public DossierMedical SaveDossierMedical(DossierMedical dossierMedical){
        return dossierMedicalRepo.save(dossierMedical);
    }

    //Get all dossierMedical
    public List<DossierMedical> AllDossierMedical(){

        return this.dossierMedicalRepo.findAll();
    }

    //Get dossierMedical By Name
    public Optional<DossierMedical> GetDossierByNumeroDossierMedicale(UUID numeroD){
        return this.dossierMedicalRepo.findByNumeroDossierMedicale(numeroD);

    }

    //Delete dossier Medical By numeroD
    public void deleteByNumeroDossierMedicale(UUID numeroD){
        this.dossierMedicalRepo.deleteByNumeroDossierMedicale(numeroD);
    }



}
