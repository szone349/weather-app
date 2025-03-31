package com.weatherapp.weatherdemo.model.openmateo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Weather(@JsonProperty("current") CurrentWeather currentWeather, @JsonProperty("daily") Daily daily,
        String place, String retrievedTime) {

}
