package org.example.airplane.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.airplane.model.PlaneTypesModel;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

@RequestScoped
@Named
public class PlaneTypeList {
    private final PlaneTypeService service;
    private PlaneTypesModel planeTypes;
    private final ModelFunctionFactory factory;

    @Inject
    public PlaneTypeList(PlaneTypeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public PlaneTypesModel getPlaneTypes() {
        if (planeTypes == null) {
            planeTypes = factory.planeTypesToModel().apply(service.findAll());
        }
        return planeTypes;
    }
}
