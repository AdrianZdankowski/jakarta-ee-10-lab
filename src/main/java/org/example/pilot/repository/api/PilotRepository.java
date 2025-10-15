package org.example.pilot.repository.api;

import org.example.pilot.entity.Pilot;
import org.example.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PilotRepository extends Repository<Pilot, UUID> {
    Optional<Pilot> findByPilotName(String pilotName);
}
