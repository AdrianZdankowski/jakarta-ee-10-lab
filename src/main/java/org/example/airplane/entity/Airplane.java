package org.example.airplane.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.pilot.entity.Pilot;

import java.io.Serializable;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plane_type_id")
    @ToString.Exclude
    private PlaneType planeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id")
    @ToString.Exclude
    private Pilot pilot;

}
