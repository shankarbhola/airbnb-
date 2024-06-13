package com.airbnb.payload;

import com.airbnb.entity.Country;
import com.airbnb.entity.Location;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyDTO {

    private Long id;


    @NotNull(message = "Property name cannot be null")
    private String propertyName;

    @NotNull(message = "guests cannot be null")
    private Integer guests;

    @NotNull(message = "bedrooms cannot be null")
    private Integer bedrooms;

    @NotNull(message = "beds cannot be null")
    private Integer beds;

    @NotNull(message = "bathrooms cannot be null")
    private Integer bathrooms;

    @NotNull(message = "Nightly price cannot be null")
    private Double nightlyPrice;

    private Country country;
    private Location location;

}
