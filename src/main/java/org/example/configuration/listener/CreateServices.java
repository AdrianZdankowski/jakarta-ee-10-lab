package org.example.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.datastore.component.AvatarStore;
import org.example.datastore.component.DataStore;
import org.example.pilot.repository.api.PilotRepository;
import org.example.pilot.repository.memory.PilotInMemoryRepository;
import org.example.pilot.service.PilotService;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");
        AvatarStore avatarStore = (AvatarStore) event.getServletContext().getAttribute("avatarStore");

        PilotRepository pilotRepository = new PilotInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("pilotService", new PilotService(pilotRepository, avatarStore));
    }
}
