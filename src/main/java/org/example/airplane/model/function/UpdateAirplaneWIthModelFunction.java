package org.example.airplane.model.function;

import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateAirplaneWIthModelFunction implements BiFunction<Airplane, AirplaneEditModel, Airplane>, Serializable {
    @Override
    public Airplane apply(Airplane entity, AirplaneEditModel request) {
        return Airplane.builder()
                .id(entity.getId())
                .name(request.getName())
                .yearOfProduction(request.getYearOfProduction())
                .flightHours(request.getFlightHours())
                .planeType(entity.getPlaneType())
                .build();
    }
}
