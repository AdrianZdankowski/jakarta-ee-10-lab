package org.example.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;
import org.example.pilot.service.PilotService;

import java.time.LocalDate;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {
    private PilotService pilotService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        pilotService = (PilotService) event.getServletContext().getAttribute("pilotService");
        init();
    }

    @SneakyThrows
    private void init() {
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
    }
}
