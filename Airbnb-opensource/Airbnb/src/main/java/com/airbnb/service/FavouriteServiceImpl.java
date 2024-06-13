package com.airbnb.service;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import com.airbnb.payload.FavouriteDto;
import com.airbnb.repository.FavouriteRepository;
import com.airbnb.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private PropertyRepository propertyRepository;
    private FavouriteRepository favouriteRepository;
    private ModelMapper modelMapper;

    public FavouriteServiceImpl(PropertyRepository propertyRepository, FavouriteRepository favouriteRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.favouriteRepository = favouriteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FavouriteDto addFav(long PropertyId, User user) {
        Property property = propertyRepository.findById(PropertyId).get();

        Favourite favourite = new Favourite();
        favourite.setUser(user);
        favourite.setProperty(property);
        Favourite checkFavourite = favouriteRepository.checkFavouriteExists(user, property);
        if (checkFavourite == null) {
            favourite.setIsFav(true);
            Favourite savedFavourite = favouriteRepository.save(favourite);
            FavouriteDto dto =modelMapper.map(savedFavourite, FavouriteDto.class);
            return dto;
        } else {
            favouriteRepository.deleteById(checkFavourite.getId());
            return null;
        }

    }

    @Override
    public List<FavouriteDto> getAllFavouritesOfUser(User user) {
        List<Favourite> allFavOfAppUser = favouriteRepository.findAllByAppUser(user);
        List<FavouriteDto> favouriteDtos = allFavOfAppUser.stream().map(favourite -> modelMapper.map(favourite, FavouriteDto.class)).collect(Collectors.toList());
        return favouriteDtos;
    }
}
