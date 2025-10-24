package org.example.airplane.view;

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

    private final AirplaneService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private AirplaneModel airplane;

    @Inject
    public AirplaneView(AirplaneService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Airplane> entity = service.find(id);
        if (entity.isPresent()) {
            this.airplane = factory.airplaneToModel().apply(entity.get());
        } else {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Airplane not found");
        }
    }
}
