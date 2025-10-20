package org.example.airplane.dto.function;

import org.example.airplane.dto.PutPlaneTypeRequest;
import org.example.airplane.entity.PlaneType;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPlaneTypeFunction implements BiFunction<UUID, PutPlaneTypeRequest, PlaneType> {
    @Override
    public PlaneType apply(UUID id, PutPlaneTypeRequest request) {
        return PlaneType.builder()
                .id(id)
                .name(request.getName())
                .numberOfEngines(request.getNumberOfEngines())
                .weight(request.getNumberOfEngines())
                .build();

    }
}
