package com.innovup.meto.request;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Builder
@Data
@Jacksonized
@ToString
public class SurgeryDurationRequest {

    @NotNull
    private final Integer days;

    @NotNull
    private final Integer hours;

    @NotNull
    private final Integer minutes;

    @NotNull
    private final Integer seconds;
}
