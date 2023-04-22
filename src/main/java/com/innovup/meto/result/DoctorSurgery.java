package com.innovup.meto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Setter
@Builder(setterPrefix = "with")
@Jacksonized
@ToString
public class DoctorSurgery {

    private UUID id;

    private String name;
}