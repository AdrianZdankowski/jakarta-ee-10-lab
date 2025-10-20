package org.example.airplane.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.PlaneTypeRepository;
import org.example.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class PlaneTypeInMemoryRepository implements PlaneTypeRepository {

    private final DataStore store;

    @Inject
    public PlaneTypeInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<PlaneType> find(UUID id) {
        return store.findAllPlaneTypes().stream()
                .filter(planeType -> planeType.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<PlaneType> findAll() {
        return store.findAllPlaneTypes();
    }

    @Override
    public void create(PlaneType entity) {
        store.createPlaneType(entity);
    }

    @Override
    public void delete(PlaneType entity) {
        store.deletePlaneType(entity.getId());
    }

    @Override
    public void update(PlaneType entity) {
        store.updatePlaneType(entity);
    }
}
