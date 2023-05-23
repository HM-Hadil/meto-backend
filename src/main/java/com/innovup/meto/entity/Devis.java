package com.innovup.meto.entity;
import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.enums.DevisStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "devis", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Devis extends EntityWithSelfAssignedId<UUID> {

    @Id
    private UUID id;

    // reference will be an auto generated unique value, ex: 'RF-123456789'
    // private AtomicLong reference;

    private BigDecimal cost;

    private LocalDate createdOn;

    private boolean isApproved;
    private DevisStatus status;

    private LocalDate validatedOn;

    private String lastUpdatedBy;
}
