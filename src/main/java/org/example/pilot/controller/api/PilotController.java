package org.example.pilot.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.pilot.dto.GetPilotResponse;
import org.example.pilot.dto.GetPilotsResponse;
import org.example.pilot.dto.PatchPilotRequest;
import org.example.pilot.dto.PutPilotRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface PilotController {

    @GET
    @Path("/pilots/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPilotResponse getPilot(@PathParam("id") UUID id);

    @GET
    @Path("/pilots")
    @Produces(MediaType.APPLICATION_JSON)
    GetPilotsResponse getPilots();

    @PUT
    @Path("/pilots/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putPilot(@PathParam("id") UUID id, PutPilotRequest request);

    @PATCH
    @Path("/pilots/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchPilot(@PathParam("id") UUID id, PatchPilotRequest request);

    @DELETE
    @Path("/pilots/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void deletePilot(@PathParam("id") UUID id);

    void putPilotAvatar(UUID id, InputStream avatar);

    byte[] getPilotAvatar(UUID id);

    void deletePilotAvatar(UUID id);

}
