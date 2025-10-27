package org.example.airplane.model.function;

import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.PlaneTypeModel;

import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlaneTypeToModelFunction implements Function<PlaneType, PlaneTypeModel> , Serializable {

    private final AirplaneToModelFunction airplaneToModelFunction;

    public PlaneTypeToModelFunction(AirplaneToModelFunction airplaneToModelFunction) {
        this.airplaneToModelFunction = airplaneToModelFunction;
    }

    @Override
    public PlaneTypeModel apply(PlaneType entity) {
        return PlaneTypeModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberOfEngines(entity.getNumberOfEngines())
                .weight(entity.getWeight())
                .airplanes(entity.getAirplanes() == null ?
                        java.util.List.of() :
                        entity.getAirplanes().stream()
                                .map(airplaneToModelFunction)
                                .collect(Collectors.toList()))
                .build();
    }
}
