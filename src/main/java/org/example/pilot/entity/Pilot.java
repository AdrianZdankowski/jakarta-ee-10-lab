package org.example.pilot.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.airplane.entity.Airplane;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Pilot implements Serializable {
    private UUID id;
    private String pilotName;
    private LocalDate accountCreationDate;
    private PilotRank role;
//    @ToString.Exclude
//    private List<Airplane> airplanes;
}
