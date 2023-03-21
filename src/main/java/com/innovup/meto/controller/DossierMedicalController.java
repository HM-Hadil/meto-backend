package com.innovup.meto.controller;

import com.innovup.meto.prendreRendezVous.entity.DossierMedical;
import com.innovup.meto.repository.DossierMedicalRepo;
import com.innovup.meto.service.DossierMedicalService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@AllArgsConstructor
@NoArgsConstructor
//@CrossOrigin( origins = {"http://localhost:4200"})
@RequestMapping("/dossierMedical/")
public class DossierMedicalController {
    @Autowired
    private DossierMedicalService service;
    @Autowired
    private DossierMedicalRepo dossierMedicalRepo;

    @PostMapping("/addDossier")
    public DossierMedical AddDossier(@RequestBody DossierMedical dossierMedical){
        return this.service.SaveDossierMedical(dossierMedical);
    }

    //Get All Dossiers medical
    @GetMapping("getAllDossiers")
    public ResponseEntity<List<DossierMedical>> getAllDossiers() {
        List<DossierMedical> dossierMedicals = dossierMedicalRepo.findAll();
        if (dossierMedicals.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dossierMedicals);
        }
    }

    //Get Chirurgie By Id
    @GetMapping("/GetDossierByNumero/{numeroDossierMedicale}")
    public ResponseEntity<DossierMedical> GetDossierByNumeroDossierMedicale(@PathVariable("numeroDossierMedicale") UUID numeroD) throws Exception {
        Optional<DossierMedical> dossier = service.GetDossierByNumeroDossierMedicale(numeroD);

        if(dossier.isPresent())  {

            return new ResponseEntity<>(dossier.get(), HttpStatus.OK);
        } else {
            throw new Exception();
        }

    }

    //Delete dossier By numeroDossierMedicale
    @DeleteMapping("/DeleteDossier/{numeroDossierMedicale}")
    public void deleteDossier(@PathVariable UUID numeroD) {
        if (dossierMedicalRepo.existsBynumeroDossierMedicale(numeroD)) {
            service.deleteByNumeroDossierMedicale(numeroD);
        } else {
            throw new RuntimeException("Dossier not found for numero dossier: " + numeroD);
        }
    }
}
