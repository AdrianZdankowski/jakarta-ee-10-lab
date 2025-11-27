package org.example.airplane.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import org.example.configuration.interceptor.LogOperationInterceptor;
import org.example.configuration.interceptor.binding.LogOperation;
import org.example.pilot.entity.PilotRoles;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.airplane.repository.api.PlaneTypeRepository;
import org.example.pilot.entity.Pilot;
import org.example.pilot.repository.api.PilotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final PlaneTypeRepository planeTypeRepository;
    private final PilotRepository pilotRepository;
    private final SecurityContext securityContext;

    @Inject
    public AirplaneService(
            AirplaneRepository airplaneRepository,
            PlaneTypeRepository planeTypeRepository,
            PilotRepository pilotRepository,
            SecurityContext securityContext) {
        this.airplaneRepository = airplaneRepository;
        this.planeTypeRepository = planeTypeRepository;
        this.pilotRepository = pilotRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public Optional<Airplane> find(UUID id) {
        if (securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            return airplaneRepository.find(id);
        }
        String login = securityContext.getCallerPrincipal().getName();
        Pilot currentPilot = pilotRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
        return airplaneRepository.findByIdAndPilot(id, currentPilot);
    }

    public Optional<Airplane> find(Pilot pilot, UUID id) {
        return airplaneRepository.findByIdAndPilot(id, pilot);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public Optional<Airplane> findForUpdate(UUID id) {
        return airplaneRepository.find(id);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public List<Airplane> findAll() {
        if (securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            return airplaneRepository.findAll();
        }
        String login = securityContext.getCallerPrincipal().getName();
        Pilot currentPilot = pilotRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
        return airplaneRepository.findAllByPilot(currentPilot);
    }

    public List<Airplane> findAll(Pilot pilot) {
        return airplaneRepository.findAllByPilot(pilot);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    @Interceptors(LogOperationInterceptor.class)
    @LogOperation("CREATE_AIRPLANE")
    public void create(Airplane airplane) {
        if (airplaneRepository.find(airplane.getId()).isPresent()) {
            throw new IllegalArgumentException("Airplane already exists.");
        }

        PlaneType planeType = planeTypeRepository.find(airplane.getPlaneType().getId())
                .orElseThrow(() -> new IllegalArgumentException("Plane type does not exists."));

        String login = securityContext.getCallerPrincipal().getName();
        Pilot currentPilot = pilotRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));

        airplane.setPlaneType(planeType);
        airplane.setPilot(currentPilot);

        airplaneRepository.create(airplane);

        planeType.getAirplanes().add(airplane);
        currentPilot.getAirplanes().add(airplane);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    @Interceptors(LogOperationInterceptor.class)
    @LogOperation("UPDATE_AIRPLANE")
    public void update(Airplane airplane) {
        Airplane existingAirplane = airplaneRepository.find(airplane.getId())
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found"));
        
        if (!securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            String login = securityContext.getCallerPrincipal().getName();
            Pilot currentPilot = pilotRepository.findByLogin(login)
                    .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
            
            if (existingAirplane.getPilot() == null || !existingAirplane.getPilot().equals(currentPilot)) {
                throw new IllegalArgumentException("User can only update their own airplanes");
            }

            airplane.setPilot(existingAirplane.getPilot());
        }
        
        airplaneRepository.update(airplane);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    @Interceptors(LogOperationInterceptor.class)
    @LogOperation("DELETE_AIRPLANE")
    public void delete(UUID id) {
        Airplane airplane = airplaneRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("Airplane not found."));

        if (!securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            String login = securityContext.getCallerPrincipal().getName();
            Pilot currentPilot = pilotRepository.findByLogin(login)
                    .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
            
            if (airplane.getPilot() == null || !airplane.getPilot().equals(currentPilot)) {
                throw new IllegalArgumentException("User can only delete their own airplanes");
            }
        }

        airplane.getPlaneType().getAirplanes().remove(airplane);

        airplaneRepository.delete(airplane);
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public Optional<List<Airplane>> findAllByPlaneType(UUID id) {
        if (securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            return planeTypeRepository.find(id)
                    .map(airplaneRepository::findAllByPlaneType);
        }
        String login = securityContext.getCallerPrincipal().getName();
        Pilot currentPilot = pilotRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
        return planeTypeRepository.find(id)
                .map(planeType -> airplaneRepository.findAllByPlaneType(planeType).stream()
                        .filter(airplane -> airplane.getPilot() != null && airplane.getPilot().equals(currentPilot))
                        .toList());
    }

    @RolesAllowed({PilotRoles.ADMIN, PilotRoles.USER})
    public Optional<List<Airplane>> findAllByPilot(UUID id) {
        if (securityContext.isCallerInRole(PilotRoles.ADMIN)) {
            return pilotRepository.find(id)
                    .map(airplaneRepository::findAllByPilot);
        }
        String login = securityContext.getCallerPrincipal().getName();
        Pilot currentPilot = pilotRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));

        if (!currentPilot.getId().equals(id)) {
            throw new IllegalArgumentException("User can only access their own airplanes");
        }
        
        return pilotRepository.find(id)
                .map(airplaneRepository::findAllByPilot);
    }
}
