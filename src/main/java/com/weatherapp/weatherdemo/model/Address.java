package com.weatherapp.weatherdemo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Address {

    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid zip code")
    @NotNull(message = "required field!")
    String zip;
}
