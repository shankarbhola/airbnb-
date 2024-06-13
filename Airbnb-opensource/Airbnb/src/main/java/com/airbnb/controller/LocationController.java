package com.airbnb.controller;

import com.airbnb.entity.Location;
import com.airbnb.payload.CountryDTO;
import com.airbnb.payload.LocationDTO;
import com.airbnb.repository.LocationRepository;
import com.airbnb.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/addlocation")
    public ResponseEntity<?> addLocation(@Valid @RequestBody LocationDTO locationDTO , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LocationDTO dto = locationService.addLocation(locationDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping()
    public  ResponseEntity<?> getAllLocations(){
        List<Location> allLocations = locationService.getAllLocations();
        return new ResponseEntity<>(allLocations,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateLocation(@Valid @RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LocationDTO dto = locationService.updateLocation(locationDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
