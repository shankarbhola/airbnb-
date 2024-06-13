package com.airbnb.service;

import com.airbnb.exception.ResourceNotFound;
import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDTO;
import com.airbnb.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {



    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PropertyDTO addProperty(PropertyDTO propertyDTO) {
        Property property =modelMapper.map(propertyDTO,Property.class);
        Property savedProperty = propertyRepository.save(property);
        PropertyDTO dto = modelMapper.map(savedProperty, PropertyDTO.class);
        return dto;
    }

    @Override
    public List<Property> getAllProperty() {
        List<Property> allProperty = propertyRepository.findAll();
        return allProperty;
    }

    @Override
    public List<Property> listAllByLocationorCountry(String locationName) {
        List<Property> byCountryOrLocationName = propertyRepository.findByNameOrLocationName(locationName);
        return byCountryOrLocationName;
    }

    @Override
    public PropertyDTO getPropertyById(long id) {
        Property property = propertyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Property not found with id " + id)
        );

        return modelMapper.map(property,PropertyDTO.class);
    }




}
