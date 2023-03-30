package com.innovup.meto.entity;

import com.innovup.meto.core.data.EntityWithSelfAssignedId;
import com.innovup.meto.core.schema.ComSchemaColumnConstantName;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "surgeries", schema = "public")
@Data
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class Surgery extends EntityWithSelfAssignedId<UUID> {

    @Id
    @Column(name = ComSchemaColumnConstantName.ID)
    private UUID id;

    @Column(name = ComSchemaColumnConstantName.NAME)
    private String name;

    @Column(name = ComSchemaColumnConstantName.DESCRIPTION)
    private String description;

    @Embedded
    @Column(name = ComSchemaColumnConstantName.DURATION)
    private SurgeryDuration duration;

    /*
        - Better to make SurgeryDuration transient and persist only the duration in seconds to gain more DB space
        - used @Embedded for Learning purposes
        - TODO change SurgeryDuration to @Transient
     */
    @Transient
    private Long durationInSeconds;

    @Column(name = ComSchemaColumnConstantName.IMAGE)
    private String image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_surgeries",
            joinColumns = {
                    @JoinColumn(
                            foreignKey = @ForeignKey(name = "fk_surgery_ref_user"),
                            name = ComSchemaColumnConstantName.SURGERY_ID,
                            referencedColumnName = ComSchemaColumnConstantName.ID
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            foreignKey = @ForeignKey(name = "fk_user_ref_surgery"),
                            name = ComSchemaColumnConstantName.USER_ID,
                            referencedColumnName = ComSchemaColumnConstantName.ID
                    )
            }
    )
    private List<User> users;
}
