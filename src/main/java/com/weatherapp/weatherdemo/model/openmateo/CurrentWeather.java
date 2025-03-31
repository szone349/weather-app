package com.weatherapp.weatherdemo.model.openmateo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentWeather(
        @JsonProperty("temperature_2m") String temperature,
        String time) {

}
