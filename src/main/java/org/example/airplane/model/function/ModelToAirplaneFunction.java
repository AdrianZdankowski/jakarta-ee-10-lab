package org.example.airplane.model.function;

import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.AirplaneCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToAirplaneFunction implements Function<AirplaneCreateModel, Airplane>, Serializable {

    @Override
    public Airplane apply(AirplaneCreateModel model) {
        return Airplane.builder()
                .id(model.getId())
                .name(model.getName())
                .yearOfProduction(model.getYearOfProduction())
                .flightHours(model.getFlightHours())
                .planeType(PlaneType.builder()
                        .id(model.getPlaneType().getId())
                        .build())
                .build();
    }
}
