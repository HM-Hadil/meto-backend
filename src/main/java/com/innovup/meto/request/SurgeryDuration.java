package com.innovup.meto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@Jacksonized
public class SurgeryDuration {

    private final Integer days;
    private final Integer hours;
    private final Integer minutes;
    private final Integer seconds;
}
