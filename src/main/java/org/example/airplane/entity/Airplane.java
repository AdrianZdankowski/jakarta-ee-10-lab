package org.example.airplane.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.pilot.entity.Pilot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "airplanes")
public class Airplane implements Serializable {
    @Id
    private UUID id;

    private String name;
    private int yearOfProduction;
    private int flightHours;

    @Version
    private Long version;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plane_type_id")
    @ToString.Exclude
    private PlaneType planeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id")
    @ToString.Exclude
    private Pilot pilot;

    @PrePersist
    public void updateCreationDateTime() {
        creationDateTime = LocalDateTime.now();
        modificationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void updateModificationDateTime() {
        modificationDateTime = LocalDateTime.now();
    }

}
