package com.weatherapp.weatherdemo.model.openmateo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Daily(
        @JsonProperty("temperature_2m_max") String[] high,
        @JsonProperty("temperature_2m_min") String[] low) {
}
