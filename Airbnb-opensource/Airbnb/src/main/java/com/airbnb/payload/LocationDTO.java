package com.airbnb.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationDTO {
    private long id;

    @NotNull(message = "Location name cannot be null")
    @Size(min = 2, max = 50, message = "Location name must be between 2 and 50 characters")
    private String locationName;
}
