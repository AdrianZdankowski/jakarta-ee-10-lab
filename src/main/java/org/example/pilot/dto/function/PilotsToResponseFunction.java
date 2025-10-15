package org.example.pilot.dto.function;

import org.example.pilot.dto.GetPilotsResponse;
import org.example.pilot.entity.Pilot;

import java.util.List;
import java.util.function.Function;

public class PilotsToResponseFunction implements Function<List<Pilot>, GetPilotsResponse> {

    @Override
    public GetPilotsResponse apply(List<Pilot> pilots) {
        return GetPilotsResponse.builder()
                .pilots(pilots.stream()
                        .map(pilot -> GetPilotsResponse.Pilot.builder()
                                .id(pilot.getId())
                                .pilotName(pilot.getPilotName())
                                .build())
                        .toList())
                .build();
    }
}
