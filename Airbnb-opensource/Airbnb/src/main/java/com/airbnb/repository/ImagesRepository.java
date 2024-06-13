package com.airbnb.repository;

import com.airbnb.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    @Query("select i from Images i where i.property.id = ?1")
    List<Images> findByPropertyId(Long id);

}