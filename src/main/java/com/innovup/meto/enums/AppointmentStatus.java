package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum  AppointmentStatus {
    CREATED("C"),
    IN_PROGRESS("P"),
    REJECTED("X"),
    ACCEPTED("A")
    ;

    private final String dbCode;

    @NonNull
    String getDbCode() { return dbCode; }

    @NonNull
    static AppointmentStatus fromDbCode( String dbCode) {
        for(var status : values()) {
            if (status.dbCode.equals(dbCode)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for AppointmentStatus");
    }


}
