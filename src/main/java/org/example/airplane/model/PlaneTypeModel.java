package org.example.airplane.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlaneTypeModel implements Serializable {
    private UUID id;
    private String name;
    private int numberOfEngines;
    private double weight;

    @Singular
    private List<AirplaneModel> airplanes;
}
