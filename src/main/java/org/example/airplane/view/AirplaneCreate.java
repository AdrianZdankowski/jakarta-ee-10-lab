package org.example.airplane.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.airplane.entity.Airplane;
import org.example.airplane.model.AirplaneCreateModel;
import org.example.airplane.model.PlaneTypeModel;
import org.example.airplane.service.AirplaneService;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
@NoArgsConstructor(force = true)
public class AirplaneCreate implements Serializable {
    private final AirplaneService airplaneService;
    private final PlaneTypeService planeTypeService;
    private final ModelFunctionFactory factory;

    @Getter
    private AirplaneCreateModel airplane;

    @Getter
    private List<PlaneTypeModel> planeTypes;

    @Inject
    public AirplaneCreate(AirplaneService airplaneService,
                          PlaneTypeService planeTypeService,
                          ModelFunctionFactory factory) {
        this.airplaneService = airplaneService;
        this.planeTypeService = planeTypeService;
        this.factory = factory;
    }

    public void init() {
        airplane = AirplaneCreateModel.builder()
                .id(UUID.randomUUID())
                .build();

        planeTypes = planeTypeService.findAll().stream()
                .map(factory.planeTypeToModel())
                .collect(Collectors.toList());
    }

    public String saveAction() {
        Airplane entity = factory.modelToAirplane().apply(airplane);
        airplaneService.create(entity);
        UUID planeTypeId = entity.getPlaneType().getId();
        return "/planetype/planetype_view.xhtml?faces-redirect=true&amp;id=" + planeTypeId;
    }
}
