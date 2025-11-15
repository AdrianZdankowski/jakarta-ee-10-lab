package org.example.airplane.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.PlaneTypeModel;
import org.example.airplane.model.PlaneTypesModel;
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

    private PlaneTypeService planeTypeService;
    private AirplaneService airplaneService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlaneTypeModel planeType;

    @Inject
    public PlaneTypeView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setPlaneTypeService(PlaneTypeService planeTypeService) {
        this.planeTypeService = planeTypeService;
    }

    @EJB
    public void setAirplaneService(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
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

    public String deleteAirplane(UUID id) {
        airplaneService.delete(id);
        return "planetype_view?faces-redirect=true&amp;id=" + planeType.getId();
    }

}
