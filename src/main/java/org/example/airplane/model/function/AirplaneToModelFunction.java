package org.example.airplane.model.function;

import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneModel;

import java.io.Serializable;
import java.util.function.Function;

public class AirplaneToModelFunction implements Function<Airplane, AirplaneModel>, Serializable {

    @Override
    public AirplaneModel apply(Airplane entity) {
        return AirplaneModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .yearOfProduction(entity.getYearOfProduction())
                .flightHours(entity.getFlightHours())
                .planeType(entity.getPlaneType() != null ? entity.getPlaneType().getName() : null)
                .build();
    }
}
