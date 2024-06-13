package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDTO;
import com.airbnb.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyDTO propertyDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PropertyDTO dto = propertyService.addProperty(propertyDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProperty(){
        List<Property> allProperty = propertyService.getAllProperty();
        return new ResponseEntity<>(allProperty, HttpStatus.OK);
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<?> listAllByLocationorCountry(@PathVariable String locationName){
        List<Property> properties = propertyService.listAllByLocationorCountry(locationName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable("id") long id){
        PropertyDTO property = propertyService.getPropertyById(id);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }


}
