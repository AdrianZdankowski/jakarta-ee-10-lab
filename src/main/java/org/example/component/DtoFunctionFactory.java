package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.airplane.dto.function.*;
import org.example.pilot.dto.function.PilotToResponseFunction;
import org.example.pilot.dto.function.PilotsToResponseFunction;
import org.example.pilot.dto.function.RequestToPilotFunction;
import org.example.pilot.dto.function.UpdatePilotWithRequestFunction;

@ApplicationScoped
public class DtoFunctionFactory {
    public PilotToResponseFunction pilotToResponse() {
        return new PilotToResponseFunction();
    }

    public PilotsToResponseFunction pilotsToResponse() {
        return new PilotsToResponseFunction();
    }

    public RequestToPilotFunction requestToPilot() {
        return new RequestToPilotFunction();
    }

    public UpdatePilotWithRequestFunction updatePilot() {
        return new UpdatePilotWithRequestFunction();
    }

    public AirplaneToResponseFunction airplaneToResponse() {
        return new AirplaneToResponseFunction();
    }

    public AirplanesToResponseFunction airplanesToResponse() {
        return new AirplanesToResponseFunction();
    }

    public RequestToAirplaneFunction requestToAirplane() {
        return new RequestToAirplaneFunction();
    }

    public UpdateAirplaneWithRequestFunction updateAirplane() {
        return new UpdateAirplaneWithRequestFunction();
    }

    public PlaneTypeToResponseFunction planeTypeToResponse() {
        return new PlaneTypeToResponseFunction();
    }

    public PlaneTypesToResponseFunction planeTypesToResponse() {
        return new PlaneTypesToResponseFunction();
    }

    // TODO: MIGHT DELETE THIS
    public RequestToPlaneTypeFunction requestToPlaneType() {
        return new RequestToPlaneTypeFunction();
    }

    // TODO: MIGHT DELETE THIS
    public UpdatePlaneTypeWithRequestFunction updatePlaneType() {
        return new UpdatePlaneTypeWithRequestFunction();
    }

}
