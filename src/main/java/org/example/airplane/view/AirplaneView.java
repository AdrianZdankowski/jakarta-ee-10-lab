package org.example.airplane.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneModel;
import org.example.airplane.service.AirplaneService;
import org.example.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class AirplaneView implements Serializable {

    private AirplaneService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private AirplaneModel airplane;

    @Inject
    public AirplaneView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(AirplaneService service) {
        this.service = service;
    }

    public void init() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getExternalContext().getUserPrincipal() == null) {
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/planetype/planetype_list.xhtml");
            return;
        }
        
        try {
            if (id == null) {
                throw new IllegalArgumentException("Missing airplane id");
            }
            
            Optional<Airplane> entity = service.find(id);
            if (entity.isEmpty()) {
                throw new IllegalArgumentException("Airplane not found");
            }
            
            this.airplane = factory.airplaneToModel().apply(entity.get());
        } catch (IllegalArgumentException e) {
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/error/404.xhtml");
        }
    }
}
