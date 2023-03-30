package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RendezVousStatus {
    CREATED("C"),
    UPDATED("X")
    ;

    private final String dbCode;

    @NonNull
    String getDbCode() { return dbCode; }

    @NonNull
    static RendezVousStatus fromDbCode(@NonNull String dbCode) {
        for(var status : values()) {
            if (status.dbCode.equals(dbCode)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for RendezVousStatus");
    }
}
