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

    @Min(value = 1914, message = "Rok produkcji musi być większy lub równy 1914")
    @Max(value = 2025, message = "Rok produkcji musi być mniejszy lub równy 2025")
    private int yearOfProduction;

    @NotNull
    @Min(value = 0, message = "Godziny lotu muszą być większe lub równe 0")
    private int flightHours;

    @NotNull(message = "Typ samolotu musi być wybrany")
    private PlaneTypeModel planeType;
}
