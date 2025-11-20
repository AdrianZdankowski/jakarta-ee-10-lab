package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.airplane.dto.function.*;
import org.example.pilot.dto.function.*;

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

    public UpdatePilotPasswordWithRequestFunction updatePilotPassword() {
        return new UpdatePilotPasswordWithRequestFunction();
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

    public RequestToPlaneTypeFunction requestToPlaneType() {
        return new RequestToPlaneTypeFunction();
    }

    public UpdatePlaneTypeWithRequestFunction updatePlaneType() {
        return new UpdatePlaneTypeWithRequestFunction();
    }

}
