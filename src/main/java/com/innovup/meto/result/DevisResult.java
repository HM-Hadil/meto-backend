package com.innovup.meto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class DevisResult {
    private final UUID id;
    private final double cost;
    private final LocalDate createdOn;
    private final boolean isApproved;
    private final LocalDate validatedOn;
    private final LocalDate lastUpdatedOn;
    private final String lastUpdatedBy;
}