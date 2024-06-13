package com.airbnb.service;

import com.airbnb.exception.ResourceNotFound;
import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDTO;
import com.airbnb.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {


    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryDTO addCountry(CountryDTO countryDTO) {
        Country checkCountry = countryRepository.findByCountryName(countryDTO.getCountryName());
        if (checkCountry==null){
            Country country = modelMapper.map(countryDTO,Country.class);
            Country savedCountry = countryRepository.save(country);
            CountryDTO dto = modelMapper.map(savedCountry, CountryDTO.class);
            return dto;
        }
        throw new ResourceNotFound("Country already exist with name "+countryDTO.getCountryName());
    }

    @Override
    public List<CountryDTO> getAllCountry() {
        List<Country> allCountry = countryRepository.findAll();
        List<CountryDTO> countryDtos = allCountry.stream().map(country -> modelMapper.map(country, CountryDTO.class)).collect(Collectors.toList());
        return countryDtos;
    }

    @Override
    public void deleteCountryById(long id) {

        countryRepository.deleteById(id);
    }

    @Override
    public CountryDTO findCountryById(long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("No Country Found")
        );
        CountryDTO dto = modelMapper.map(country, CountryDTO.class);
        return dto;
    }

    @Override
    public CountryDTO updateCountry(long id, CountryDTO countryDTO) {
        countryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("No country found by name "+countryDTO.getCountryName())
        );

        Country country = modelMapper.map(countryDTO, Country.class);
        country.setId(id);
        Country updatedCountry = countryRepository.save(country);
        CountryDTO dto = modelMapper.map(updatedCountry, CountryDTO.class);
        return dto;
    }


}
