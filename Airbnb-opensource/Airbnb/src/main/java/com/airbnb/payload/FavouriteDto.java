package com.airbnb.payload;

import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import lombok.Data;

@Data
public class FavouriteDto {
    private long id;
    private Boolean isFav;
    private Property property;
    private User user;
}
