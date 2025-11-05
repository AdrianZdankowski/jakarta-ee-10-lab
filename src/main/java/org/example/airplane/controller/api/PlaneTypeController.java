package org.example.airplane.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.airplane.dto.GetPlaneTypeResponse;
import org.example.airplane.dto.GetPlaneTypesResponse;
import org.example.airplane.dto.PatchPlaneTypeRequest;
import org.example.airplane.dto.PutPlaneTypeRequest;

import java.util.UUID;

@Path("")
public interface PlaneTypeController {

    @GET
    @Path("/planetypes")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlaneTypesResponse getPlaneTypes();

    @GET
    @Path("/planetypes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlaneTypeResponse getPlaneType(@PathParam("id") UUID id);

    @PUT
    @Path("/planetypes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putPlaneType(@PathParam("id") UUID id, PutPlaneTypeRequest request);

    @PATCH
    @Path("/planetypes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response patchPlaneType(@PathParam("id") UUID id, PatchPlaneTypeRequest request);

    @DELETE
    @Path("/planetypes/{id}")
    Response deletePlaneType(@PathParam("id") UUID id);
}
