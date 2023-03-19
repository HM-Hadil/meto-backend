package com.innovup.meto.entity;

import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryDuration {

    @Column(name = ComSchemaColumnConstantName.C_DURATION_DAYS)
    private Integer days;

    @Column(name = ComSchemaColumnConstantName.C_DURATION_HOURS)
    private Integer hours;

    @Column(name = ComSchemaColumnConstantName.C_DURATION_MINUTES)
    private Integer minutes;

    @Column(name = ComSchemaColumnConstantName.C_DURATION_SECONDS)
    private Integer seconds;
}
