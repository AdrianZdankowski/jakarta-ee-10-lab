package org.example.pilot.dto.function;

import org.example.pilot.dto.PatchPilotRequest;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;

import java.util.function.BiFunction;

public class UpdatePilotWithRequestFunction implements BiFunction<Pilot, PatchPilotRequest, Pilot> {

    @Override
    public Pilot apply(Pilot entity, PatchPilotRequest request) {
        return Pilot.builder()
                .id(entity.getId())
                .pilotName(request.getPilotName())
                .role(PilotRank.valueOf(request.getRole().toUpperCase()))
                .accountCreationDate(entity.getAccountCreationDate())
                .build();
    }
}
