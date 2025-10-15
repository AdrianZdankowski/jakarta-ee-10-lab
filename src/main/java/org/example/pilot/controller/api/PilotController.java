package org.example.pilot.controller.api;

import org.example.pilot.dto.GetPilotResponse;
import org.example.pilot.dto.GetPilotsResponse;
import org.example.pilot.dto.PatchPilotRequest;
import org.example.pilot.dto.PutPilotRequest;

import java.io.InputStream;
import java.util.UUID;

public interface PilotController {

    GetPilotResponse getPilot(UUID id);

    GetPilotsResponse getPilots();

    void putPilot(UUID id, PutPilotRequest request);

    void patchPilot(UUID id, PatchPilotRequest request);
    void deletePilot(UUID id);

    void putPilotAvatar(UUID id, InputStream avatar);

    byte[] getPilotAvatar(UUID id);

    void deletePilotAvatar(UUID id);

}
