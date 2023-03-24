package com.innovup.meto.entity;

import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryDuration {

    @Column(name = ComSchemaColumnConstantName.DURATION_DAYS)
    private Integer days;

    @Column(name = ComSchemaColumnConstantName.DURATION_HOURS)
    private Integer hours;

    @Column(name = ComSchemaColumnConstantName.DURATION_MINUTES)
    private Integer minutes;

    @Column(name = ComSchemaColumnConstantName.DURATION_SECONDS)
    private Integer seconds;
}
