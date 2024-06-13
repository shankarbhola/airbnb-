package com.airbnb.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryDTO {
    private Long id;

    @NotNull(message = "Country name cannot be null")
    private String countryName;
}
