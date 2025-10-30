package org.example.airplane.controller.simple;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.airplane.controller.api.AirplaneController;
import org.example.airplane.dto.GetAirplaneResponse;
import org.example.airplane.dto.GetAirplanesResponse;
import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.dto.PutAirplaneRequest;
import org.example.airplane.service.AirplaneService;
import org.example.component.DtoFunctionFactory;

import java.util.UUID;

@RequestScoped
public class AirplaneSimpleController implements AirplaneController {

    private final AirplaneService service;
    private final DtoFunctionFactory factory;

    @Inject
    public AirplaneSimpleController(AirplaneService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public GetAirplanesResponse getAirplanes() {
        return factory.airplanesToResponse().apply(service.findAll());
    }

    @Override
    public GetAirplanesResponse getPlaneTypeAirplanes(UUID id) {
        return service.findAllByPlaneType(id)
                .map(factory.airplanesToResponse())
                .orElseThrow(() -> new NotFoundException("No airplanes found in plane type id %s".formatted(id)));
    }

    @Override
    public GetAirplanesResponse getPilotAirplanes(UUID id) {
        return service.findAllByPilot(id)
                .map(factory.airplanesToResponse())
                .orElseThrow(() -> new NotFoundException("No airplanes found for user id %s".formatted(id)));
    }

    @Override
    public GetAirplaneResponse getAirplane(UUID id) {
        return service.find(id)
                .map(factory.airplaneToResponse())
                .orElseThrow(() -> new NotFoundException("Airplane with id %s not found".formatted(id)));
    }

    @Override
    public void putAirplane(UUID id, PutAirplaneRequest request) {
        try {
            service.create(factory.requestToAirplane().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchAirplane(UUID id, PatchAirplaneRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateAirplane().apply(entity, request)),
                () -> {
                    throw new NotFoundException("Airplane with id %s not found".formatted(id));
                }
        );
    }

    @Override
    public void deleteAirplane(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Airplane with id %s not found".formatted(id));
                }
        );
    }
}
