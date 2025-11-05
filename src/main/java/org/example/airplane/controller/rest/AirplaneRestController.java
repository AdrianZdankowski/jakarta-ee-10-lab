package org.example.airplane.controller.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.example.airplane.controller.api.AirplaneController;
import org.example.airplane.dto.GetAirplaneResponse;
import org.example.airplane.dto.GetAirplanesResponse;
import org.example.airplane.dto.PatchAirplaneRequest;
import org.example.airplane.dto.PutAirplaneRequest;
import org.example.airplane.service.AirplaneService;
import org.example.component.DtoFunctionFactory;

import java.net.URI;
import java.util.UUID;

@Path("")
public class AirplaneRestController implements AirplaneController {

    private final AirplaneService service;
    private final DtoFunctionFactory factory;

    @Inject
    public AirplaneRestController(AirplaneService service, DtoFunctionFactory factory) {
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
    public Response putAirplane(UUID typeId, UUID id, PutAirplaneRequest request) {
        try {
            boolean exists = service.find(id).isPresent();

            if (!exists) {
                service.create(factory.requestToAirplane().apply(typeId, id, request));
                return Response.created(URI.create(String.format("/api/planetypes/%s/airplanes/%s", typeId, id)))
                        .build();
            }
            else {
                return Response.noContent().build();
            }
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public Response patchAirplane(UUID id, PatchAirplaneRequest request, UUID typeId) {
        return service.find(id).map(entity -> {
            service.update(factory.updateAirplane().apply(entity, request, typeId));
            return Response.ok().build();
        })
                .orElseThrow(() -> new NotFoundException("Airplane with id %s not found".formatted(id)));
    }

    @Override
    public Response deleteAirplane(UUID id) {
        return service.find(id).map(entity -> {
            service.delete(id);
            return Response.ok().build();
        })
                .orElseThrow(() -> new NotFoundException("Airplane with id %s not found".formatted(id)));
    }
}
