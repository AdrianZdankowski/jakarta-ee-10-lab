package org.example.airplane.dto.function;

import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.entity.Airplane;

import java.util.function.BiFunction;

public class UpdateAirplaneWithRequestFunction implements BiFunction<Airplane, PatchAirplaneRequest, Airplane> {
    @Override
    public Airplane apply(Airplane entity, PatchAirplaneRequest request) {
        return Airplane.builder()
                .id(entity.getId())
                .name(request.getName())
                .yearOfProduction(request.getYearOfProduction())
                .flightHours(request.getFlightHours())
                .planeType(entity.getPlaneType())
                .build();
    }
}
