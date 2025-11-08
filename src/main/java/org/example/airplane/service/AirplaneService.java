package org.example.airplane.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.airplane.repository.api.PlaneTypeRepository;
import org.example.pilot.entity.Pilot;
import org.example.pilot.repository.api.PilotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final PlaneTypeRepository planeTypeRepository;
    private final PilotRepository pilotRepository;

    @Inject
    public AirplaneService(
            AirplaneRepository airplaneRepository,
            PlaneTypeRepository planeTypeRepository,
            PilotRepository pilotRepository) {
        this.airplaneRepository = airplaneRepository;
        this.planeTypeRepository = planeTypeRepository;
        this.pilotRepository = pilotRepository;
    }

    public Optional<Airplane> find(UUID id) {
        return airplaneRepository.find(id);
    }

    public Optional<Airplane> find(Pilot pilot, UUID id) {
        return airplaneRepository.findByIdAndPilot(id, pilot);
    }

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    public List<Airplane> findAll(Pilot pilot) {
        return airplaneRepository.findAllByPilot(pilot);
    }

    @Transactional
    public void create(Airplane airplane) {
        if (airplaneRepository.find(airplane.getId()).isPresent()) {
            throw new IllegalArgumentException("Airplane already exists.");
        }

        PlaneType planeType = planeTypeRepository.find(airplane.getPlaneType().getId())
                .orElseThrow(() -> new IllegalArgumentException("Plane type does not exists."));
        Pilot pilot = pilotRepository.find(airplane.getPilot().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Pilot does not exists."));

        airplane.setPlaneType(planeType);
        airplane.setPilot(pilot);

        airplaneRepository.create(airplane);

        planeType.getAirplanes().add(airplane);
        pilot.getAirplanes().add(airplane);
    }

    @Transactional
    public void update(Airplane airplane) {
        airplaneRepository.update(airplane);
    }

    @Transactional
    public void delete(UUID id) {
        Airplane airplane = airplaneRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found."));

        airplane.getPlaneType().getAirplanes().remove(airplane);
        airplane.getPilot().getAirplanes().remove(airplane);

        airplaneRepository.delete(airplane);
    }

    public Optional<List<Airplane>> findAllByPlaneType(UUID id) {
        return planeTypeRepository.find(id)
                .map(airplaneRepository::findAllByPlaneType);
    }

    public Optional<List<Airplane>> findAllByPilot(UUID id) {
        return pilotRepository.find(id)
                .map(airplaneRepository::findAllByPilot);
    }
}
