package org.example.pilot.dto.function;

import org.example.pilot.dto.GetPilotResponse;
import org.example.pilot.entity.Pilot;

import java.util.function.Function;

public class PilotToResponseFunction implements Function<Pilot, GetPilotResponse> {

    @Override
    public GetPilotResponse apply(Pilot pilot) {
        return GetPilotResponse.builder()
                .id(pilot.getId())
                .login(pilot.getLogin())
                .pilotName(pilot.getPilotName())
                .rank(pilot.getRank() != null ? pilot.getRank().name() : null)
                .accountCreationDate(pilot.getAccountCreationDate())
                .build();
    }
}
