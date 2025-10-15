package org.example.pilot.controller.simple;

import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.BadRequestException;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.pilot.controller.api.PilotController;
import org.example.pilot.dto.GetPilotResponse;
import org.example.pilot.dto.GetPilotsResponse;
import org.example.pilot.dto.PatchPilotRequest;
import org.example.pilot.dto.PutPilotRequest;
import org.example.pilot.entity.Pilot;
import org.example.pilot.service.PilotService;

import java.io.InputStream;
import java.util.UUID;

public class PilotSimpleController implements PilotController {

    private final PilotService service;
    private final DtoFunctionFactory factory;

    public PilotSimpleController(PilotService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetPilotResponse getPilot(UUID id) {
        return service.find(id)
                .map(factory.pilotToResponse())
                .orElseThrow(() -> new NotFoundException("Pilot with id %s does not exist.".formatted(id)));
    }

    @Override
    public GetPilotsResponse getPilots() {
        return factory.pilotsToResponse().apply(service.findAll());
    }

    @Override
    public void putPilot(UUID id, PutPilotRequest request) {
        try {
            service.create(factory.requestToPilot().apply(id,request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Given data is incorrect");
        }
    }

    @Override
    public void patchPilot(UUID id, PatchPilotRequest request) {
        service.find(id).ifPresentOrElse(
                pilot -> service.update(factory.updatePilot().apply(pilot, request)),
                () -> {
                    throw new NotFoundException("Pilot with id %s does not exist.".formatted(id));
                }
        );
    }

    @Override
    public void deletePilot(UUID id) {
        service.find(id).ifPresentOrElse(
                pilot -> {
                    try {
                        service.deleteAvatar(id);
                    }
                    catch (IllegalArgumentException ex) {

                    }
                    service.delete(id);
                },
                () -> {
                    throw new NotFoundException("Pilot with id %s does not exist.".formatted(id));
                }
        );
    }

    @Override
    public void putPilotAvatar(UUID id, InputStream avatar) {
        service.find(id).ifPresentOrElse(
                entity -> service.updateAvatar(id, avatar),
                () -> {
                    throw new NotFoundException("Pilot with id %s does not exist.".formatted(id));
                }
        );
    }

    @Override
    public byte[] getPilotAvatar(UUID id) {
//        return service.find(id)
////                .map(Pilot::getAvatar)
////                .orElseThrow(() -> new NotFoundException("Pilot does not exist or has no avatar"));
        service.find(id)
                .orElseThrow(() -> new NotFoundException("Pilot with id %s does not exist.".formatted(id)));

        try {
            return service.loadAvatar(id);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException("Avatar for pilot with id %s not found".formatted(id));
        }
    }

    @Override
    public void deletePilotAvatar(UUID id) {
        service.find(id).ifPresentOrElse(
                pilot -> service.deleteAvatar(id),
                () -> {
                    throw new NotFoundException("Pilot with id %s does not exist.".formatted(id));
                }
                );
    }
}
