package org.example.pilot.dto.function;

import org.example.pilot.dto.PutPilotRequest;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPilotFunction implements BiFunction<UUID, PutPilotRequest, Pilot> {

    @Override
    public Pilot apply(UUID id, PutPilotRequest request) {
        return Pilot.builder()
                .id(id)
                .login(request.getLogin())
                .password(request.getPassword())
                .pilotName(request.getPilotName())
                .rank(PilotRank.valueOf(request.getRank().toUpperCase()))
                .accountCreationDate(LocalDate.now())
                .build();
    }
}
