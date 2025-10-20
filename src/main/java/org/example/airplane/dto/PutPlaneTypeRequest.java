package org.example.airplane.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPlaneTypeRequest {
    private String name;
    private int numberOfEngines;
    private double weight;
}
