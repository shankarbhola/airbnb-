package com.airbnb.service;

import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    CountryDTO addCountry(CountryDTO countryDTO);
    List<CountryDTO> getAllCountry();
    void deleteCountryById(long id);
    CountryDTO findCountryById(long id);
    CountryDTO updateCountry(long id,CountryDTO countryDTO);
}
