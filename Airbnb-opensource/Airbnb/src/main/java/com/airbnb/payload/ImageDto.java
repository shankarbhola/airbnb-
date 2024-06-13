package com.airbnb.payload;

import com.airbnb.entity.Property;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String imageUrl;
}
