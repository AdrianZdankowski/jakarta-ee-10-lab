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
public class GetAirplaneResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class PlaneType {
        private UUID id;
        private String name;
    }

    private UUID id;
    private String name;
    private int yearOfProduction;
    private int flightHours;
    private PlaneType planeType;

}
