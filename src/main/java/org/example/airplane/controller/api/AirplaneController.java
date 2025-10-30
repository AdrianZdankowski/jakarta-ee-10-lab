package org.example.airplane.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @PUT
    @Path("/planetypes/{typeId}/airplanes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putAirplane(@PathParam("typeId") UUID typeId, @PathParam("id") UUID id, PutAirplaneRequest request);

    @PATCH
    @Path("/planetypes/{typeId}/airplanes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response patchAirplane(@PathParam("id") UUID id, PatchAirplaneRequest request, @PathParam("typeId") UUID typeId);

    @DELETE
    @Path("/planetypes/airplanes/{id}")
    Response deleteAirplane(@PathParam("id") UUID id);
}
