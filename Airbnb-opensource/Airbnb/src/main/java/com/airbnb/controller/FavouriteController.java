package com.airbnb.controller;

import com.airbnb.entity.User;
import com.airbnb.payload.FavouriteDto;
import com.airbnb.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorite")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    //http://localhost:8080/api/v1/favorite/addFav?propertyId=1
    @PostMapping("/addFav")
    public ResponseEntity<?> addFavourite( @AuthenticationPrincipal User user, @RequestParam long propertyId){
        FavouriteDto dto = favouriteService.addFav(propertyId, user);
        if (dto!=null){
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Removed From Favourite", HttpStatus.OK);
    }

    @GetMapping("/userFavList")
    public ResponseEntity<?> getAllFavouritesOfUser(@AuthenticationPrincipal User user){
        List<FavouriteDto> allFavouritesOfUser = favouriteService.getAllFavouritesOfUser(user);
        if (!allFavouritesOfUser.isEmpty()){
            return new ResponseEntity<>(allFavouritesOfUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Favourites Found", HttpStatus.OK);
    }
}
