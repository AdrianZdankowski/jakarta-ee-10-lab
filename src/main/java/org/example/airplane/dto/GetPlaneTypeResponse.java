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
public class GetPlaneTypeResponse {
    private UUID id;
    private String name;
    private int numberOfEngines;
    private double weight;
}
