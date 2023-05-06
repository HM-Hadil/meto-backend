package com.innovup.meto.result;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
@AllArgsConstructor
@ToString
public class OpinionResult {
    private final UUID id;
    private final String message;
    private LocalDateTime createdOn;

    private  PatientResult patient;
    private final String image;
}
