package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {

    ADMIN("A"),
    PATIENT("P"),
    DOCTOR("D")
    ;

    private final String dbCode;

    @NonNull
    String getDbCode() { return dbCode; }

    @NonNull
    static Role fromDbCode(@NonNull String dbCode) {
        for(var role : values()) {
            if (role.dbCode.equals(dbCode)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for UserRole");
    }
}
