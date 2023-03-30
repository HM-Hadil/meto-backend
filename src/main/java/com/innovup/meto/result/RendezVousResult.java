package com.innovup.meto.result;

import com.innovup.meto.enums.RendezVousStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@ToString
public class RendezVousResult {

    private UUID id;

    private RendezVousStatus status;

    private LocalDateTime date;

    private LocalDateTime createdOn;

    private LocalDateTime lastUpdatedOn;

}
