package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String dbCode;


    String getDbCode() { return dbCode; }


    static Gender fromDbCode( String dbCode) {
        for(var gender : values()) {
            if (gender.dbCode.equals(dbCode)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for Gender");
    }
}
