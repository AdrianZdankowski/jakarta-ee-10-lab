package org.example.airplane.controller.api;

import org.example.airplane.dto.GetAirplaneResponse;
import org.example.airplane.dto.GetAirplanesResponse;
import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.dto.PutAirplaneRequest;

import java.util.UUID;

public interface AirplaneController {
    GetAirplanesResponse getAirplanes();

    GetAirplanesResponse getPlaneTypeAirplanes(UUID id);

    GetAirplanesResponse getPilotAirplanes(UUID id);

    GetAirplaneResponse getAirplane(UUID id);

    void putAirplane(UUID id, PutAirplaneRequest request);

    void patchAirplane(UUID id, PatchAirplaneRequest request);

    void deleteAirplane(UUID id);
}
