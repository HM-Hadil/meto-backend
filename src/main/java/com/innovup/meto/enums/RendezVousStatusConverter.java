package com.innovup.meto.enums;

import javax.persistence.AttributeConverter;

public class RendezVousStatusConverter implements AttributeConverter<RendezVousStatus, String> {

    @Override
    public String convertToDatabaseColumn(RendezVousStatus status) {
        if (status == null) {
            return null;
        }
        return status.getDbCode();
    }

    @Override
    public RendezVousStatus convertToEntityAttribute(String dbCode) {
        return RendezVousStatus.fromDbCode(dbCode);
    }
}
