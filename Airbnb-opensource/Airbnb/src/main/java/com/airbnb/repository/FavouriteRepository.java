package com.airbnb.repository;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("select f from Favourite f where f.user =:user and f.property=:property")
    Favourite checkFavouriteExists(@Param("user") User user, @Param("property") Property property);

    @Query("select f from Favourite f where f.user =:user")
    List<Favourite> findAllByAppUser(@Param("user") User user);
}