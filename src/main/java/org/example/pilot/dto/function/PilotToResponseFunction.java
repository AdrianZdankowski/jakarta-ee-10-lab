package org.example.pilot.dto.function;

import org.example.pilot.dto.GetPilotResponse;
import org.example.pilot.entity.Pilot;

import java.util.function.Function;

public class PilotToResponseFunction implements Function<Pilot, GetPilotResponse> {

    @Override
    public GetPilotResponse apply(Pilot pilot) {
        return GetPilotResponse.builder()
                .id(pilot.getId())
                .pilotName(pilot.getPilotName())
                .role(pilot.getRole() != null ? pilot.getRole().name() : null)
                .accountCreationDate(pilot.getAccountCreationDate())
                .build();
    }
}
