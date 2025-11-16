package org.example.airplane.service;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.PlaneTypeRepository;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class PlaneTypeService {
    private final PlaneTypeRepository repository;

    @Inject
    public PlaneTypeService(PlaneTypeRepository repository) {
        this.repository = repository;
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public Optional<PlaneType> find(UUID id) {
        Optional<PlaneType> planeType = repository.find(id);
        //planeType.ifPresent(value -> log.info("Number or plane types: %d".formatted(value.getAirplanes().size())));
        return planeType;
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public List<PlaneType> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(PilotRoles.ADMIN)
    public void create(PlaneType planeType) {
        repository.create(planeType);
    }

    @RolesAllowed(PilotRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @RolesAllowed(PilotRoles.ADMIN)
    public void update(PlaneType entity) {
        repository.update(entity);
    }
}
