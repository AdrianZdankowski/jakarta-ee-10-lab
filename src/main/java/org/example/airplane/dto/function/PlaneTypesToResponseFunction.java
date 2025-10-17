package org.example.airplane.dto.function;

import org.example.airplane.dto.GetPlaneTypesResponse;
import org.example.airplane.entity.PlaneType;

import java.util.List;
import java.util.function.Function;

public class PlaneTypesToResponseFunction implements Function<List<PlaneType>, GetPlaneTypesResponse> {
    @Override
    public GetPlaneTypesResponse apply(List<PlaneType> entites) {
        return GetPlaneTypesResponse.builder()
                .planeTypes(entites.stream()
                        .map(planeType -> GetPlaneTypesResponse.PlaneType.builder()
                                .id(planeType.getId())
                                .name(planeType.getName())
                                .build())
                        .toList())
                .build();
    }
}
