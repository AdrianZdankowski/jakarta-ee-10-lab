package org.example.airplane.entity;

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
public class Airplane implements Serializable {
    private UUID id;
    private String name;
    private int yearOfProduction;
    private int flightHours;
    private PlaneType planeType;
    @ToString.Exclude
    private Pilot pilot;

}
