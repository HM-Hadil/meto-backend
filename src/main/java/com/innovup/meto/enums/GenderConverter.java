package com.innovup.meto.enums;

import javax.persistence.AttributeConverter;

public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getDbCode();
    }

    @Override
    public Gender convertToEntityAttribute(String dbCode) {
        return Gender.fromDbCode(dbCode);
    }
}
