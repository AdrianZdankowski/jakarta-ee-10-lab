package org.example.airplane.controller.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.airplane.dto.GetAirplaneResponse;
import org.example.airplane.dto.GetAirplanesResponse;
import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.dto.PutAirplaneRequest;

import java.util.UUID;

@Path("")
public interface AirplaneController {

    @GET
    @Path("/planetypes/airplanes")
    @Produces(MediaType.APPLICATION_JSON)
    GetAirplanesResponse getAirplanes();

    @GET
    @Path("/planetypes/{id}/airplanes")
    @Produces(MediaType.APPLICATION_JSON)
    GetAirplanesResponse getPlaneTypeAirplanes(@PathParam("id") UUID id);

    @GET
    @Path("/pilots/{id}/airplanes")
    @Produces(MediaType.APPLICATION_JSON)
    GetAirplanesResponse getPilotAirplanes(@PathParam("id") UUID id);

    @GET
    @Path("/planetypes/airplanes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetAirplaneResponse getAirplane(@PathParam("id") UUID id);

    void putAirplane(UUID id, PutAirplaneRequest request);

    void patchAirplane(UUID id, PatchAirplaneRequest request);

    void deleteAirplane(UUID id);
}
