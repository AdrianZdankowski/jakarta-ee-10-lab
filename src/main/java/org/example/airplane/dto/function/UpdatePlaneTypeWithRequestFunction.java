package org.example.airplane.dto.function;

import org.example.airplane.dto.PatchPlaneTypeRequest;
import org.example.airplane.entity.PlaneType;

import java.util.function.BiFunction;

public class UpdatePlaneTypeWithRequestFunction implements BiFunction<PlaneType, PatchPlaneTypeRequest, PlaneType> {
    @Override
    public PlaneType apply(PlaneType entity, PatchPlaneTypeRequest request) {
        return PlaneType.builder()
                .id(entity.getId())
                .name(request.getName())
                .numberOfEngines(request.getNumberOfEngines())
                .weight(request.getWeight())
                .airplanes(entity.getAirplanes())
                .build();
    }
}
