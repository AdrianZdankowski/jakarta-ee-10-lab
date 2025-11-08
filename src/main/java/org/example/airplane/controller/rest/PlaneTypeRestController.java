package org.example.airplane.controller.rest;

import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.example.airplane.controller.api.PlaneTypeController;
import org.example.airplane.dto.GetPlaneTypeResponse;
import org.example.airplane.dto.GetPlaneTypesResponse;
import org.example.airplane.dto.PatchPlaneTypeRequest;
import org.example.airplane.dto.PutPlaneTypeRequest;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.DtoFunctionFactory;


import java.net.URI;
import java.util.UUID;

@Path("")
public class PlaneTypeRestController implements PlaneTypeController {

    private final PlaneTypeService service;
    private final DtoFunctionFactory factory;

    @Inject
    public PlaneTypeRestController(PlaneTypeService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetPlaneTypesResponse getPlaneTypes() {
        return factory.planeTypesToResponse().apply(service.findAll());

    }

    @Override
    public GetPlaneTypeResponse getPlaneType(UUID id) {
        return service.find(id)
                .map(factory.planeTypeToResponse())
                .orElseThrow(
                        () -> new NotFoundException("Plane type with id %s not found".formatted(id)));
    }

    @Override
    public Response putPlaneType(UUID id, PutPlaneTypeRequest request) {
        try {
            boolean exists = service.find(id).isPresent();
            service.create(factory.requestToPlaneType().apply(id, request));

            if (!exists) {
                return Response.created(URI.create("/api/planetypes/" + id)).build();
            } else {
                return Response.ok().build();
            }
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public Response patchPlaneType(UUID id, PatchPlaneTypeRequest request) {
        try {
            return service.find(id).map(entity -> {
                service.update(factory.updatePlaneType().apply(entity, request));
                return Response.ok().build();
            }).orElseThrow(() -> new NotFoundException("Plane type with id %s not found".formatted(id)));
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public Response deletePlaneType(UUID id) {
        try {
            return service.find(id).map(entity -> {
                service.delete(id);
                return Response.ok().build();
            }).orElseThrow(() -> new NotFoundException("Plane type with id %s not found".formatted(id)));
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }
}
