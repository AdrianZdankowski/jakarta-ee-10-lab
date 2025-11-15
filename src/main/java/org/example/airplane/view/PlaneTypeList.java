package org.example.airplane.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.airplane.model.PlaneTypesModel;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

@RequestScoped
@Named
public class PlaneTypeList {
    private PlaneTypeService service;
    private PlaneTypesModel planeTypes;
    private final ModelFunctionFactory factory;

    @Inject
    public PlaneTypeList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(PlaneTypeService service) {
        this.service = service;
    }

    public PlaneTypesModel getPlaneTypes() {
        if (planeTypes == null) {
            planeTypes = factory.planeTypesToModel().apply(service.findAll());
        }
        return planeTypes;
    }

    public String deleteAction(PlaneTypesModel.PlaneType planeType) {
        service.delete(planeType.getId());
        return "planetype_list?faces-redirect=true";
    }
}
