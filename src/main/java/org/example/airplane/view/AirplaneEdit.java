package org.example.airplane.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneEditModel;
import org.example.airplane.model.PlaneTypeModel;
import org.example.airplane.service.AirplaneService;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class AirplaneEdit implements Serializable {
    private AirplaneService airplaneService;
//    private final PlaneTypeService planeTypeService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private AirplaneEditModel airplane;

    @Getter
    private PlaneTypeModel planeType;

    @Inject
    public AirplaneEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setAirplaneService(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    public void init() throws IOException {
        Optional<Airplane> entity = airplaneService.find(id);
        if (entity.isPresent()) {
            this.airplane = factory.airplaneToEditModel().apply(entity.get());
            this.planeType = factory.planeTypeToModel().apply(entity.get().getPlaneType());
        } else {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .responseSendError(404, "Airplane not found");
        }
    }

    public String saveAction() {
        airplaneService.update(factory.updateAirplane().apply(airplaneService.find(id).orElseThrow(), airplane));
        return "/planetype/planetype_view.xhtml?id=" + planeType.getId() + "&faces-redirect=true";
    }
}
