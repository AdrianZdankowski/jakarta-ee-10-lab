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

    public synchronized List<PlaneType> findAllPlaneTypes() {
        return planeTypes.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPlaneType(PlaneType value) throws IllegalArgumentException {
        if (planeTypes.stream().anyMatch(planeType -> planeType.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("Plane type with id \"%s\" already exists".formatted(value.getId()));
        }
        planeTypes.add(cloningUtility.clone(value));
    }

    public synchronized void updatePlaneType(PlaneType value) throws IllegalArgumentException {
        PlaneType existingType = planeTypes.stream()
                .filter(pt -> pt.getId().equals(value.getId()))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(
                        "PlaneType with id \"%s\" does not exist".formatted(value.getId())
                        ));

        existingType.setName(value.getName());
        existingType.setNumberOfEngines(value.getNumberOfEngines());
        existingType.setWeight(value.getWeight());

        airplanes.stream()
                .filter(airplane -> airplane.getPlaneType() != null &&
                        airplane.getPlaneType().getId().equals(existingType.getId()))
                .forEach(airplane -> airplane.setPlaneType(existingType));

        // TODO: Czy na pewno potrzebne?
        List<Airplane> relatedAirplanes = airplanes.stream()
                .filter(airplane -> airplane.getPlaneType() != null &&
                        airplane.getPlaneType().getId().equals(existingType.getId()))
                .collect(Collectors.toList());
        existingType.setAirplanes(relatedAirplanes);
    }

    public synchronized void deletePlaneType(UUID id) throws IllegalArgumentException {
        if (!planeTypes.removeIf(pt -> pt.getId().equals(id))) {
            throw new IllegalArgumentException("PlaneType with id \"%s\" does not exist".formatted(id));
        }

        List<Airplane> planesToRemove = airplanes.stream()
                .filter(airplane -> airplane.getPlaneType() != null &&
                        airplane.getPlaneType().getId().equals(id))
                .toList();

        airplanes.removeAll(planesToRemove);

        for (Pilot pilot : pilots) {
            if (pilot.getAirplanes() != null) {
                pilot.getAirplanes().removeIf(planesToRemove::contains);
            }
        }
    }

    public synchronized List<Airplane> findAllAirplanes() {
        return airplanes.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createAirplane(Airplane value) throws IllegalArgumentException {
        if (airplanes.stream().anyMatch(airplane -> airplane.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("Airplane with id \"%s\" already exists".formatted(value.getId()));
        }
        Airplane entity = cloneWithRelationships(value);
        airplanes.add(entity);
    }

    public synchronized void updateAirplane(Airplane value) throws IllegalArgumentException {
        Airplane entity = cloneWithRelationships(value);
        if (airplanes.removeIf(airplane -> airplane.getId().equals(value.getId()))) {
            airplanes.add(entity);
        }
        else {
            throw new IllegalArgumentException("Airplane with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteAirplane(UUID id) throws IllegalArgumentException {
        if (!airplanes.removeIf(airplane -> airplane.getId().equals(id))) {
            throw new IllegalArgumentException("Airplane with id \"%s\" does not exist".formatted(id));
        }
    }

    private Airplane cloneWithRelationships(Airplane value) {
        Airplane entity = cloningUtility.clone(value);

        if (entity.getPilot() != null) {
            entity.setPilot(pilots.stream()
                    .filter(pilot -> pilot.getId().equals(value.getPilot().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No pilot with id \"%s\".".formatted(value.getPilot().getId()))));
        }

        if (entity.getPlaneType() != null) {
            entity.setPlaneType(planeTypes.stream()
                    .filter(planeType -> planeType.getId().equals(value.getPlaneType().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No plane type with id \"%s\".".formatted(value.getPlaneType().getId()))));
        }

        return entity;
    }
}
