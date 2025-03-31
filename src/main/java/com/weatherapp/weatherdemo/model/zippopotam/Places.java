package com.weatherapp.weatherdemo.model.zippopotam;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Places(
                @JsonProperty("place name") String placeName,
                String longitude,
                String state,
                @JsonProperty("state abbreviation") String stateAbbreviation,
                String latitude) {

}
