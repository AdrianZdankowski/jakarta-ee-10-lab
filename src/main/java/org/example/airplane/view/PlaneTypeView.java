package org.example.airplane.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.PlaneTypeModel;
import org.example.airplane.service.AirplaneService;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class PlaneTypeView implements Serializable {

    private final PlaneTypeService planeTypeService;
    private final AirplaneService airplaneService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlaneTypeModel planeType;

    @Inject
    public PlaneTypeView(PlaneTypeService planeTypeService,
                         AirplaneService airplaneService,
                         ModelFunctionFactory factory) {
        this.planeTypeService = planeTypeService;
        this.airplaneService = airplaneService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<PlaneType> planeType = planeTypeService.find(id);
        if (planeType.isPresent()) {
            this.planeType = factory.planeTypeToModel().apply(planeType.get());

            airplaneService.findAllByPlaneType(id)
                    .ifPresent(airplanes ->
                            this.planeType.setAirplanes(
                                    airplanes.stream()
                                            .map(factory.airplaneToModel())
                                            .toList()
                            )
                    );
        } else {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Plane type not found");
        }
    }
}
