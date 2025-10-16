package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
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
}
