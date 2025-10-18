package org.example.airplane.controller.api;

import org.example.airplane.dto.GetPlaneTypeResponse;
import org.example.airplane.dto.GetPlaneTypesResponse;
import org.example.airplane.dto.PatchPlaneTypeRequest;
import org.example.airplane.dto.PutPlaneTypeRequest;

import java.util.UUID;

public interface PlaneTypeController {
    GetPlaneTypesResponse getPlaneTypes();

    GetPlaneTypeResponse getPlaneType(UUID id);

    void putPlaneType(UUID id, PutPlaneTypeRequest request);

    void patchPlaneType(UUID id, PatchPlaneTypeRequest request);

    void deletePlaneType(UUID id);
}
