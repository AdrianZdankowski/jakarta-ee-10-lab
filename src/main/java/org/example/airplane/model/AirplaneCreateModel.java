package org.example.airplane.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.airplane.validation.binding.ValidAirplaneName;

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

    @ValidAirplaneName
    private String name;

    @Min(1914)
    @Max(2025)
    private int yearOfProduction;

    @NotNull
    @Min(0)
    private int flightHours;

    @NotNull
    private PlaneTypeModel planeType;
}
