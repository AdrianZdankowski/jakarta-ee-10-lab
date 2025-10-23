package org.example.airplane.model.function;

import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.PlaneTypesModel;

import java.util.List;
import java.util.function.Function;

public class PlaneTypesToModelFunction implements Function<List<PlaneType>, PlaneTypesModel> {
    @Override
    public PlaneTypesModel apply(List<PlaneType> entity) {
        return PlaneTypesModel.builder()
                .planeTypes(entity.stream()
                        .map(planeType -> PlaneTypesModel.PlaneType.builder()
                                .id(planeType.getId())
                                .name(planeType.getName())
                                .build())
                        .toList())
                .build();
    }
}
