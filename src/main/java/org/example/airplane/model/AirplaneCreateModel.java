package org.example.airplane.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AirplaneCreateModel {
    private UUID id;
    private String name;
    private int yearOfProduction;
    private int flightHours;
    private PlaneTypeModel planeType;
}
