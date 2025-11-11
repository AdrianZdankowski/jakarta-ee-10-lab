package org.example.airplane.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.PlaneTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
@Log
public class PlaneTypeService {
    private final PlaneTypeRepository repository;

    @Inject
    public PlaneTypeService(PlaneTypeRepository repository) {
        this.repository = repository;
    }

    public Optional<PlaneType> find(UUID id) {
        Optional<PlaneType> planeType = repository.find(id);
        //planeType.ifPresent(value -> log.info("Number or plane types: %d".formatted(value.getAirplanes().size())));
        return planeType;
    }

    public List<PlaneType> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(PlaneType planeType) {
        repository.create(planeType);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @Transactional
    public void update(PlaneType entity) {
        repository.update(entity);
    }
}
