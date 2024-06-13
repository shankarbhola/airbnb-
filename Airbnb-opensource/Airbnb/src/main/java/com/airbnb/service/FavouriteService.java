package com.airbnb.service;

import com.airbnb.entity.User;
import com.airbnb.payload.FavouriteDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FavouriteService {
    public FavouriteDto addFav(long PropertyId, User user);
    public List<FavouriteDto> getAllFavouritesOfUser(User user);
}
