package org.example.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.component.DtoFunctionFactory;
import org.example.pilot.controller.simple.PilotSimpleController;
import org.example.pilot.service.PilotService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PilotService pilotService = (PilotService) event.getServletContext().getAttribute("pilotService");


        event.getServletContext().setAttribute("pilotController", new PilotSimpleController(
                pilotService,
                new DtoFunctionFactory()
        ));
    }
}
