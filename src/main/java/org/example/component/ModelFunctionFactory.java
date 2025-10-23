package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.airplane.model.function.AirplaneToModelFunction;
import org.example.airplane.model.function.PlaneTypeToModelFunction;
import org.example.airplane.model.function.PlaneTypesToModelFunction;

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
}
