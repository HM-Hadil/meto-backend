package com.innovup.meto.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Jacksonized
@ToString
public class AppointmentCountDTO {
    private Long count;
    private LocalDate month;
    private UUID doctorId;

    public AppointmentCountDTO( long count, LocalDate month, UUID doctorId ) {
        this.count = count;
        this.month = month;
        this.doctorId = doctorId;
    }
}
