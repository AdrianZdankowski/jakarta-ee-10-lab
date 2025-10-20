package org.example.airplane.dto.function;

import org.example.airplane.dto.GetPlaneTypeResponse;
import org.example.airplane.entity.PlaneType;

import java.util.function.Function;

public class PlaneTypeToResponseFunction implements Function<PlaneType, GetPlaneTypeResponse> {
    @Override
    public GetPlaneTypeResponse apply(PlaneType entity) {
        return GetPlaneTypeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEngines(entity.getNumberOfEngines())
                .weight(entity.getWeight())
                .build();
    }
}
