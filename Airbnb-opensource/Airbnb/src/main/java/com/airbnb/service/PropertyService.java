package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDTO;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    public PropertyDTO addProperty(PropertyDTO propertyDTO);
    public List<Property> getAllProperty();

    public List<Property> listAllByLocationorCountry(String locationName);
    public PropertyDTO getPropertyById(long id);
}
