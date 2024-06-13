package com.airbnb.payload;

import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;

    private String content;

    @Size(min = 1, max = 5)
    private Integer rating;
}
