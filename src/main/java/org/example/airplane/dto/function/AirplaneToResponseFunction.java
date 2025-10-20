package org.example.airplane.dto.function;

import org.example.airplane.dto.GetAirplaneResponse;
import org.example.airplane.entity.Airplane;

import java.util.function.Function;

public class AirplaneToResponseFunction implements Function<Airplane, GetAirplaneResponse> {

    @Override
    public GetAirplaneResponse apply(Airplane entity) {
        return GetAirplaneResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .yearOfProduction(entity.getYearOfProduction())
                .flightHours(entity.getFlightHours())
                .planeType(GetAirplaneResponse.PlaneType.builder()
                        .id(entity.getPlaneType().getId())
                        .name(entity.getPlaneType().getName())
                        .build())
                .build();
    }
}
