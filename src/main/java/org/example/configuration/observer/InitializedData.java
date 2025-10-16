package org.example.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;
import org.example.pilot.service.PilotService;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    private PilotService pilotService;

    private final RequestContextController requestContextController;


    @Inject
    public InitializedData(
            PilotService pilotService,
            RequestContextController requestContextController
    ) {
        this.pilotService = pilotService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        Pilot general = Pilot.builder()
                .id(UUID.fromString("39afa67e-1728-4050-8327-0cd92e715565"))
                .pilotName("Jan Kowalski")
                .accountCreationDate(LocalDate.now())
                .role(PilotRank.CAPTAIN)
                .build();

        Pilot major = Pilot.builder()
                .id(UUID.fromString("cb7057a8-ff08-430a-a909-fdc84f177db3"))
                .pilotName("Robert Mak")
                .accountCreationDate(LocalDate.now())
                .role(PilotRank.MAJOR)
                .build();

        Pilot officer = Pilot.builder()
                .id(UUID.fromString("8c9ccdaa-c35f-496a-8e6b-d44a25e83be9"))
                .pilotName("Emilia Earhart")
                .accountCreationDate(LocalDate.now())
                .role(PilotRank.OFFICER)
                .build();

        Pilot otherPilot = Pilot.builder()
                .id(UUID.fromString("3d85ad37-3d29-4f1a-b95d-7bd059304065"))
                .pilotName("Adam Nos")
                .accountCreationDate(LocalDate.now())
                .role(PilotRank.OFFICER)
                .build();

        pilotService.create(general);
        pilotService.create(major);
        pilotService.create(officer);
        pilotService.create(otherPilot);

        requestContextController.deactivate();
    }
}
