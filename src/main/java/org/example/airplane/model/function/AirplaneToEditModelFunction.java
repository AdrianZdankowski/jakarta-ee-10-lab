package org.example.airplane.model.function;

import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class AirplaneToEditModelFunction implements Function<Airplane, AirplaneEditModel>, Serializable {
    @Override
    public AirplaneEditModel apply(Airplane entity) {
        return AirplaneEditModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .yearOfProduction(entity.getYearOfProduction())
                .flightHours(entity.getFlightHours())
                .build();
    }
}
