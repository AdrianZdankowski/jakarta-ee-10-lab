package org.example.airplane.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.model.PlaneTypeModel;
import org.example.airplane.service.PlaneTypeService;
import org.example.component.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = PlaneTypeModel.class, managed = true)
public class PlaneTypeModelConverter implements Converter<PlaneTypeModel> {
    private final PlaneTypeService service;
    private final ModelFunctionFactory factory;

    @Inject
    public PlaneTypeModelConverter(PlaneTypeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public PlaneTypeModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) return null;

        Optional<PlaneType> planeType = service.find(UUID.fromString(value));
        return planeType.map(factory.planeTypeToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PlaneTypeModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
