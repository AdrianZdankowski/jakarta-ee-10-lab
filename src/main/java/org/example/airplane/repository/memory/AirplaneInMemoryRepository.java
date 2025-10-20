package org.example.airplane.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.datastore.component.DataStore;
import org.example.pilot.entity.Pilot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class AirplaneInMemoryRepository implements AirplaneRepository {
    private final DataStore store;

    @Inject
    public AirplaneInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Airplane> find(UUID id) {
        return store.findAllAirplanes().stream()
                .filter(airplane -> airplane.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Airplane> findAll() {
        return store.findAllAirplanes();
    }

    @Override
    public void create(Airplane entity) {
        store.createAirplane(entity);
    }

    @Override
    public void delete(Airplane entity) {
        store.deleteAirplane(entity.getId());
    }

    @Override
    public void update(Airplane entity) {
        store.updateAirplane(entity);
    }

    @Override
    public Optional<Airplane> findByIdAndPilot(UUID id, Pilot pilot) {
        return store.findAllAirplanes().stream()
                .filter(airplane -> airplane.getPilot().equals(pilot))
                .filter(airplane -> airplane.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Airplane> findAllByPilot(Pilot pilot) {
        return store.findAllAirplanes().stream()
                .filter(airplane -> pilot.equals(airplane.getPilot()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findAllByPlaneType(PlaneType planeType) {
        return store.findAllAirplanes().stream()
                .filter(airplane -> planeType.equals(airplane.getPlaneType()))
                .collect(Collectors.toList());
    }

}
