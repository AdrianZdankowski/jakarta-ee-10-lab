package org.example.pilot.dto.function;

import org.example.pilot.dto.PutPasswordRequest;
import org.example.pilot.entity.Pilot;

import java.util.function.BiFunction;

public class UpdatePilotPasswordWithRequestFunction implements BiFunction<Pilot, PutPasswordRequest, Pilot> {

    @Override
    public Pilot apply(Pilot entity, PutPasswordRequest request) {
        return Pilot.builder()
                .id(entity.getId())
                .pilotName(entity.getPilotName())
                .accountCreationDate(entity.getAccountCreationDate())
                .rank(entity.getRank())
                .login(entity.getLogin())
                .password(request.getPassword())
                .build();
    }
}
