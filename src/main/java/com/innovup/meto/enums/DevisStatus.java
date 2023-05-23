package com.innovup.meto.enums;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum  DevisStatus {
    CREATED("Created"),
    CHANGED_BY_ADMIN("Updated"),
    CONFIRMED_BY_PATIENT("Confirmed") ,
    REJECTED_BY_PATIENT("Rejected") ;

    private final String dbCode;
    @NonNull
    String getDbCode() { return dbCode; }
    @NonNull
    static DevisStatus fromDbCode( String dbCode) {
        for(var status : values()) {
            if (status.dbCode.equals(dbCode)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid dbCode '" + dbCode + "'for DevisStatus");
    }

}
