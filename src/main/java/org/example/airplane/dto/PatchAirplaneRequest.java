package org.example.airplane.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchAirplaneRequest {
    private String name;
    private int yearOfProduction;
    private int flightHours;
}
