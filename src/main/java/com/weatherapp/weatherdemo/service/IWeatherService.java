package com.weatherapp.weatherdemo.service;

import com.weatherapp.weatherdemo.model.zippopotam.GeoLocation;
import com.weatherapp.weatherdemo.model.openmateo.Weather;

public interface IWeatherService {

    public Weather getWeather(String zip);

    public GeoLocation getGeoDetails(String zip);

    public Weather getWeatherFromApi(String lat, String lon);

}
