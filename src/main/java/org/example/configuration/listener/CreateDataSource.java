package org.example.configuration.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.datastore.component.AvatarStore;
import org.example.datastore.component.DataStore;
import org.example.serialization.component.CloningUtility;

@WebListener
public class CreateDataSource implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.setAttribute("datasource", new DataStore(new CloningUtility()));
        context.setAttribute("avatarStore", new AvatarStore(context));
    }
}
