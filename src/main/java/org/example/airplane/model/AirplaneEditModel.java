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
public class AirplaneEditModel {
    private UUID id;
    private String name;
    private int yearOfProduction;
    private int flightHours;
}
