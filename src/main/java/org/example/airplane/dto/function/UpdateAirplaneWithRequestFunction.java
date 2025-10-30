package org.example.airplane.dto.function;

import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateAirplaneWithRequestFunction implements TriFunction<Airplane, PatchAirplaneRequest, UUID ,Airplane> {
    @Override
    public Airplane apply(Airplane entity, PatchAirplaneRequest request, UUID typeId) {
        return Airplane.builder()
                .id(entity.getId())
                .name(request.getName())
                .yearOfProduction(request.getYearOfProduction())
                .flightHours(request.getFlightHours())
                .planeType(PlaneType.builder()
                        .id(typeId)
                        .build())
                .pilot(entity.getPilot())
                .build();
    }
}
