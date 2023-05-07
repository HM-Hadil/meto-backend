package com.innovup.meto.mapper;

import com.innovup.meto.entity.Devis;
import com.innovup.meto.result.DevisResult;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class DevisMapper {

    public DevisResult entityToResult(Devis devis) {

        if (devis == null) {
            return null;
        }

        return DevisResult.builder()
                .withId(devis.getId())
                .withCost(devis.getCost().doubleValue())
                .withCreatedOn(devis.getCreatedOn())
                .withIsApproved(devis.isApproved())
                .withValidatedOn(devis.isApproved() ? devis.getValidatedOn() : null)
                .withLastUpdatedBy(devis.getLastUpdatedBy())
                .build();
    }
}
