package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.airplane.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public PlaneTypesToModelFunction planeTypesToModel() {
        return new PlaneTypesToModelFunction();
    }

    public AirplaneToModelFunction airplaneToModel() {
        return new AirplaneToModelFunction();
    }

    public PlaneTypeToModelFunction planeTypeToModel() {
        return new PlaneTypeToModelFunction(airplaneToModel());
    }

    public AirplaneToEditModelFunction airplaneToEditModel() {
        return new AirplaneToEditModelFunction();
    }

    public ModelToAirplaneFunction modelToAirplane() {
        return new ModelToAirplaneFunction();
    }

    public UpdateAirplaneWIthModelFunction updateAirplane() {
        return new UpdateAirplaneWIthModelFunction();
    }
}
