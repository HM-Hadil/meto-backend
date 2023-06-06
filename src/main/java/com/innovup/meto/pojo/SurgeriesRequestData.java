package com.innovup.meto.pojo;

import com.innovup.meto.entity.SurgeriesRequest;
import com.innovup.meto.entity.Surgery;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder(setterPrefix = "with")
@Jacksonized
public class SurgeriesRequestData {

    private final SurgeriesRequest surgeriesRequest;

    private final Surgery surgery;
}
