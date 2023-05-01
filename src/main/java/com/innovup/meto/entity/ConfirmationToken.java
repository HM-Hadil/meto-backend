package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "confirmation_tokens", schema = "public")
@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken extends EntityWithSelfAssignedId<UUID> {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.TOKEN)
    private String token;

    @Column(name = ComSchemaColumnConstantName.EXPIRATION_DATE)
    @Builder.Default
    private Date expirationDate = calculateExpiryDate();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @Builder.Default
    private Boolean used = Boolean.FALSE;

    @Transient
    private Long expired;


    private static Date calculateExpiryDate() {
        return Date.from(LocalDateTime.now().plusMinutes(EXPIRATION).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Is expired boolean.
     *
     * @return the boolean
     */
    public boolean isExpired() {
        final Calendar cal = Calendar.getInstance();
        return this.getExpirationDate().before(cal.getTime());
    }
}
