package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String dbCode;

    @NonNull
    String getDbCode() { return dbCode; }

    @NonNull
    static Gender fromDbCode(@NonNull String dbCode) {
        for(var gender : values()) {
            if (gender.dbCode.equals(dbCode)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for Gender");
    }
}
