package org.example.airplane.repository.api;

import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.pilot.entity.Pilot;
import org.example.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirplaneRepository extends Repository<Airplane, UUID> {
    Optional<Airplane> findByIdAndPilot(UUID id, Pilot pilot);

    List<Airplane> findAllByPilot(Pilot pilot);

    List<Airplane> findAllByPlaneType(PlaneType planeType);
}
