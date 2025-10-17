package org.example.airplane.dto.function;

import org.example.airplane.dto.GetAirplanesResponse;
import org.example.airplane.entity.Airplane;

import java.util.List;
import java.util.function.Function;

public class AirplanesToResponseFunction implements Function<List<Airplane>, GetAirplanesResponse> {

    @Override
    public GetAirplanesResponse apply(List<Airplane> entites) {
        return GetAirplanesResponse.builder()
                .airplanes(entites.stream()
                        .map(airplane -> GetAirplanesResponse.Airplane.builder()
                                .id(airplane.getId())
                                .name(airplane.getName())
                                .build())
                        .toList())
                .build();
    }
}
