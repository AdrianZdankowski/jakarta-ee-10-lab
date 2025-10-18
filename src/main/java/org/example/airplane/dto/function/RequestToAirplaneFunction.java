package org.example.airplane.dto.function;

import org.example.airplane.dto.PutAirplaneRequest;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.pilot.entity.Pilot;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToAirplaneFunction implements BiFunction<UUID, PutAirplaneRequest, Airplane> {

    @Override
    public Airplane apply(UUID id, PutAirplaneRequest request) {
        return Airplane.builder()
                .id(id)
                .name(request.getName())
                .yearOfProduction(request.getYearOfProduction())
                .flightHours(request.getFlightHours())
                .planeType(PlaneType.builder()
                        .id(request.getPlaneType())
                        .build())
                .pilot(Pilot.builder()
                        .id(request.getPilot())
                        .build())
                .build();
    }
}
