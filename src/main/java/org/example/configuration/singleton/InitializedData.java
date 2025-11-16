package org.example.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.airplane.repository.api.PlaneTypeRepository;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;
import org.example.pilot.entity.PilotRoles;
import org.example.pilot.repository.api.PilotRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
@DependsOn("InitializeAdminService")
public class InitializedData {
    private PilotRepository pilotRepository;
    private PlaneTypeRepository planeTypeRepository;
    private AirplaneRepository airplaneRepository;

    @Inject
    private SecurityContext securityContext;

    @Inject
    @SuppressWarnings("CdiInjectionPointsInspection")
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    public void setPilotRepository(PilotRepository pilotRepository) {
        this.pilotRepository = pilotRepository;
    }

    @Inject
    public void setAirplaneRepository(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Inject
    public void setPlaneTypeRepository(PlaneTypeRepository planeTypeRepository) {
        this.planeTypeRepository = planeTypeRepository;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (pilotRepository.findByLogin("admin").isEmpty()) {

            Pilot general = Pilot.builder()
                    .id(UUID.fromString("39afa67e-1728-4050-8327-0cd92e715565"))
                    .login("admin")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(PilotRoles.ADMIN, PilotRoles.USER))
                    .pilotName("Jan Kowalski")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.CAPTAIN)
                    .build();

            Pilot major = Pilot.builder()
                    .id(UUID.fromString("cb7057a8-ff08-430a-a909-fdc84f177db3"))
                    .login("robert")
                    .password(passwordHash.generate("useruser".toCharArray()))
                    .roles(List.of(PilotRoles.USER))
                    .pilotName("Robert Mak")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.MAJOR)
                    .build();

            Pilot officer = Pilot.builder()
                    .id(UUID.fromString("8c9ccdaa-c35f-496a-8e6b-d44a25e83be9"))
                    .login("emilia")
                    .password(passwordHash.generate("useruser".toCharArray()))
                    .roles(List.of(PilotRoles.USER))
                    .pilotName("Emilia Earhart")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.OFFICER)
                    .build();

            Pilot otherPilot = Pilot.builder()
                    .id(UUID.fromString("3d85ad37-3d29-4f1a-b95d-7bd059304065"))
                    .login("adam")
                    .password(passwordHash.generate("useruser".toCharArray()))
                    .roles(List.of(PilotRoles.USER))
                    .pilotName("Adam Nos")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.OFFICER)
                    .build();

            pilotRepository.create(general);
            pilotRepository.create(major);
            pilotRepository.create(officer);
            pilotRepository.create(otherPilot);

            PlaneType fighterAircraft = PlaneType.builder()
                    .id(UUID.fromString("63d6f249-48ab-4d71-85bb-34ed3bd1a748"))
                    .name("Fighter")
                    .weight(9.5)
                    .numberOfEngines(1)
                    .build();

            PlaneType passengerAircraft = PlaneType.builder()
                    .id(UUID.fromString("c8e3e5ec-ee6d-4841-8f73-188dcd8a05b8"))
                    .name("Passenger")
                    .weight(100)
                    .numberOfEngines(2)
                    .build();

            PlaneType transportAircraft = PlaneType.builder()
                    .id(UUID.fromString("0c3fd662-b90d-415f-ae0c-af1554fc5e85"))
                    .name("Transport")
                    .weight(70.3)
                    .numberOfEngines(4)
                    .build();

            planeTypeRepository.create(fighterAircraft);
            planeTypeRepository.create(passengerAircraft);
            planeTypeRepository.create(transportAircraft);

            Airplane f16 = Airplane.builder()
                    .id(UUID.fromString("59934c21-368b-48fc-905a-2f34575fdff1"))
                    .name("F-16 Fighting Falcon")
                    .yearOfProduction(2022)
                    .flightHours(1245)
                    .planeType(fighterAircraft)
                    .pilot(general)
                    .build();

            Airplane f35 = Airplane.builder()
                    .id(UUID.fromString("78fa23b8-152d-47a1-aa8e-d11aa412aa64"))
                    .name("F-35 Lightning II")
                    .yearOfProduction(2024)
                    .flightHours(600)
                    .planeType(fighterAircraft)
                    .pilot(general)
                    .build();

            Airplane dreamliner = Airplane.builder()
                    .id(UUID.fromString("366830cd-ecb5-4bc6-9d55-60a06a13994f"))
                    .name("Boeing 787")
                    .yearOfProduction(2018)
                    .flightHours(12415)
                    .planeType(passengerAircraft)
                    .pilot(major)
                    .build();

            Airplane airbus = Airplane.builder()
                    .id(UUID.fromString("6fd80b80-00d9-42c9-9888-2b3f168f0dd0"))
                    .name("Airbus A320")
                    .yearOfProduction(2010)
                    .flightHours(13015)
                    .planeType(passengerAircraft)
                    .pilot(officer)
                    .build();

            Airplane c130 = Airplane.builder()
                    .id(UUID.fromString("681bc221-0a21-4f3e-a726-6cebac04b210"))
                    .name("C-130 Hercules")
                    .yearOfProduction(1995)
                    .flightHours(24542)
                    .planeType(transportAircraft)
                    .pilot(otherPilot)
                    .build();

            airplaneRepository.create(f16);
            airplaneRepository.create(f35);
            airplaneRepository.create(dreamliner);
            airplaneRepository.create(airbus);
            airplaneRepository.create(c130);
        }
    }
}
