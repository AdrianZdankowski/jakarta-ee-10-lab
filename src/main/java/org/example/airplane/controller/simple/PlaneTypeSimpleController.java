package org.example.airplane.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.airplane.controller.api.PlaneTypeController;
import org.example.airplane.dto.GetPlaneTypeResponse;
import org.example.airplane.dto.GetPlaneTypesResponse;
import org.example.airplane.dto.PatchPlaneTypeRequest;
import org.example.airplane.dto.PutPlaneTypeRequest;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.BadRequestException;
import org.example.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@RequestScoped
public class PlaneTypeSimpleController implements PlaneTypeController {
    private final PlaneTypeService service;
    private final DtoFunctionFactory factory;

    @Inject
    public PlaneTypeSimpleController(PlaneTypeService service, DtoFunctionFactory factory) {
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
                .orElseThrow(() -> new NotFoundException("Plane type with id %s not found".formatted(id)));
    }

    @Override
    public void putPlaneType(UUID id, PutPlaneTypeRequest request) {
        try {
            service.create(factory.requestToPlaneType().apply(id, request));
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchPlaneType(UUID id, PatchPlaneTypeRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updatePlaneType().apply(entity,request)),
                () -> {
                    throw new NotFoundException("Plane type with id %s not found".formatted(id));
                }
        );
    }

    @Override
    public void deletePlaneType(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Plane type with id %s not found".formatted(id));
                }
        );
    }
}
