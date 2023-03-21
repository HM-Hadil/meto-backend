package com.innovup.meto.prendreRendezVous.entity;


import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierMedical extends EntityWithSelfAssignedId<UUID> {
    @Id
    private   UUID  numeroDossierMedicale;
    private String StatusDossierMedicale;
    private String typeChirurgie;
    private String typeSang ;
    private  Double poidPatient;
    private  String patientInfo;


    @Override
    public UUID getId() {
        return this.numeroDossierMedicale;
    }
}
