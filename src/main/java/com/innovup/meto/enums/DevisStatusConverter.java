package com.innovup.meto.enums;

import javax.persistence.AttributeConverter;

public class DevisStatusConverter implements AttributeConverter<DevisStatus, String> {
    @Override
    public String convertToDatabaseColumn(DevisStatus status) {
        if (status == null) {
            return null;
        }
        return status.getDbCode();
    }
    @Override
    public DevisStatus convertToEntityAttribute(String dbCode) {
        return DevisStatus.fromDbCode(dbCode);
    }

}
