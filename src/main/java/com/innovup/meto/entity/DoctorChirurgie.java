package com.innovup.meto.entity;

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

public class DoctorChirurgie {

    private UUID id;

    private String name;
}
