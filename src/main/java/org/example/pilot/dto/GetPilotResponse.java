package org.example.pilot.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPilotResponse {
    private UUID id;
    private String pilotName;
    private LocalDate accountCreationDate;
    private String role;
}
