package com.innovup.meto.enums;

import javax.persistence.AttributeConverter;

public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentStatus status) {
        if (status == null) {
            return null;
        }
        return status.getDbCode();
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(String dbCode) {
        return AppointmentStatus.fromDbCode(dbCode);
    }
}
