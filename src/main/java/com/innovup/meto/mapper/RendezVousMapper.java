package com.innovup.meto.mapper;


import com.innovup.meto.entity.RendezVous;
import com.innovup.meto.result.RendezVousResult;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor
@Component
public class RendezVousMapper {

    public RendezVousResult entityToResult(RendezVous rendezVous) {
        return RendezVousResult.builder()
                .withId(rendezVous.getId())
                .withStatus(rendezVous.getStatus())
                .withDateRDV(rendezVous.getDateRDV())
                .withCreatedOn(rendezVous.getCreatedOn())
                .withLastUpdatedOn(rendezVous.getLastUpdatedOn())
                .build();
    }
}