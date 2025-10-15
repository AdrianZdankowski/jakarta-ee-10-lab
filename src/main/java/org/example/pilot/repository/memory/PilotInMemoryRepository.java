package org.example.pilot.repository.memory;

import org.example.datastore.component.DataStore;
import org.example.pilot.entity.Pilot;
import org.example.pilot.repository.api.PilotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PilotInMemoryRepository implements PilotRepository {
    private final DataStore store;

    public PilotInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Pilot> findByPilotName(String pilotName) {
        return store.findAllPilots().stream()
                .filter(pilot -> pilot.getPilotName().equals(pilotName))
                .findFirst();
    }

    @Override
    public Optional<Pilot> find(UUID id) {
        return store.findAllPilots().stream()
                .filter(pilot -> pilot.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Pilot> findAll() {
        return store.findAllPilots();
    }

    @Override
    public void create(Pilot entity) {
        store.createPilot(entity);
    }

    @Override
    public void delete(Pilot entity) {
        store.deletePilot(entity.getId());
    }

    @Override
    public void update(Pilot entity) {
        store.updatePilot(entity);
    }
}
