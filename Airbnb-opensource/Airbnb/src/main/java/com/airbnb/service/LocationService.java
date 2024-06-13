package com.airbnb.service;

import com.airbnb.exception.ResourceNotFound;
import com.airbnb.entity.Location;
import com.airbnb.payload.LocationDTO;
import com.airbnb.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;


    public LocationDTO addLocation(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLocationName(locationDTO.getLocationName());
        Location savedLocation = locationRepository.save(location);
        LocationDTO dto = new LocationDTO();
        dto.setId(savedLocation.getId());
        dto.setLocationName(savedLocation.getLocationName());
        return dto;
    }

    public List<Location> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations;
    }

    public LocationDTO updateLocation(LocationDTO locationDTO) {
        locationRepository.findById(locationDTO.getId()).orElseThrow(
                ()->new ResourceNotFound("No country found by name "+locationDTO.getLocationName())
        );

        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setLocationName(locationDTO.getLocationName());
        Location updatedLocation = locationRepository.save(location);
        LocationDTO dto = new LocationDTO();
        dto.setId(updatedLocation.getId());
        dto.setLocationName(updatedLocation.getLocationName());
        return dto;
    }
}
