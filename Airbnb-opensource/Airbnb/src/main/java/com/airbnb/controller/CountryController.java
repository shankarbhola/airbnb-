package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDTO;
import com.airbnb.service.CountryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("addCountry")
    public ResponseEntity<?> addCountry(@Valid @RequestBody CountryDTO countryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDTO dto = countryService.addCountry(countryDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCountry(){
        List<CountryDTO> allCountry = countryService.getAllCountry();
        if (!allCountry.isEmpty()){
            return new ResponseEntity<>(allCountry, HttpStatus.OK);
        }
        return new ResponseEntity<>("No country found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCountryById(@RequestParam long id){
        if(countryService.findCountryById(id)==null){
            return new ResponseEntity<String>("No country found", HttpStatus.BAD_REQUEST);
        }
        countryService.deleteCountryById(id);
        return new ResponseEntity<String>("Country deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountry(@Valid @RequestBody CountryDTO countryDTO, @PathVariable long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDTO dto = countryService.updateCountry(id,countryDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


}
