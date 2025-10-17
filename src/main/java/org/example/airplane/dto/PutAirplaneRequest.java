package org.example.airplane.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutAirplaneRequest {
    private String name;
    private int yearOfProduction;
    private int flightHours;
    private UUID planeType;
}
