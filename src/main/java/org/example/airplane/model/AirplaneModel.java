package org.example.airplane.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AirplaneModel implements Serializable {
    private UUID id;
    private String name;
    private int yearOfProduction;
    private int flightHours;
    private String planeType;
}