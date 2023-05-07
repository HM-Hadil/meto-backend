package com.innovup.meto.request;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;


@SuperBuilder
@Data
@Jacksonized
@Validated
@ToString
public class DevisRequest {

    private final double cost ;

    private boolean approved;
}
