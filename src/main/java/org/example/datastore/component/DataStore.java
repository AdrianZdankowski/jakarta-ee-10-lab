package org.example.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.pilot.entity.Pilot;
import org.example.serialization.component.CloningUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<PlaneType> planeTypes = new HashSet<>();

    private final Set<Airplane> airplanes = new HashSet<>();

    private final Set<Pilot> pilots = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Pilot> findAllPilots() {
        return pilots.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPilot(Pilot value) throws IllegalArgumentException {
        if (pilots.stream().anyMatch(pilot -> pilot.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The pilot with id \"%s\" already exists".formatted(value.getId()));
        }
        pilots.add(cloningUtility.clone(value));
    }

    public synchronized void updatePilot(Pilot value) throws IllegalArgumentException {
        if (pilots.removeIf(pilot -> pilot.getId().equals(value.getId()))) {
            pilots.add(cloningUtility.clone(value));
        }
        else {
            throw new IllegalArgumentException("The pilot with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deletePilot(UUID id) throws IllegalArgumentException {
        if (!pilots.removeIf(pilot -> pilot.getId().equals(id))) {
            throw new IllegalArgumentException("The pilot with id \"%s\" does not exist".formatted(id));
        }
    }
}
