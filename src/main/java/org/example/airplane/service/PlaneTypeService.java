package org.example.airplane.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.PlaneTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlaneTypeService {
    private final PlaneTypeRepository repository;

    @Inject
    public PlaneTypeService(PlaneTypeRepository repository) {
        this.repository = repository;
    }

    public Optional<PlaneType> find(UUID id) {
        return repository.find(id);
    }

    public List<PlaneType> findAll() {
        return repository.findAll();
    }

    public void create(PlaneType planeType) {
        repository.create(planeType);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(PlaneType entity) {
        repository.update(entity);
    }
}
