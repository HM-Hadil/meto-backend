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
public class UpdatePhotoReq {
    private final String image;
}
