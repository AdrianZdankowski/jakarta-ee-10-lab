package org.example.airplane.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PlaneType implements Serializable {
    private UUID id;
    private String name;
    private int numberOfEngines;
    private double weight;
    @ToString.Exclude
    private List<Airplane> airplanes;
}
