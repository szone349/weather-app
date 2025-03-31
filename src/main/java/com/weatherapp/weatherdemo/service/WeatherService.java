package com.weatherapp.weatherdemo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;

import com.weatherapp.weatherdemo.model.zippopotam.GeoLocation;
import com.weatherapp.weatherdemo.model.openmateo.Weather;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherService implements IWeatherService {
    public WeatherService(RestClient.Builder restClientBuilder,
            @Value("${openmateoApi.baseUrl}") final String weatherApiUrl,
            @Value("${zippopotamApi.baseUrl}") final String geoApiUrl) {
        this.restClientBuilder = restClientBuilder;
        this.geoApiUrl = geoApiUrl;
        this.weatherApiUrl = weatherApiUrl;
    }

    private final RestClient.Builder restClientBuilder;
    private final String weatherApiUrl;
    private final String geoApiUrl;

    /**
     * Get Weather details for a Zip, response is cached
     */
    @Override
    @Cacheable(value = "weatherCache", key = "#zip", condition = "#zip != null")
    public Weather getWeather(String zip) {
        log.info("Retrieving weather for {}", zip);
        log.info("Getting geo location for zip {}", zip);
        GeoLocation geoLocation = getGeoDetails(zip);
        String lat = geoLocation.places().get(0).latitude();
        String lon = geoLocation.places().get(0).longitude();
        String place = geoLocation.places().get(0).placeName()
                + ", " + geoLocation.places().get(0).stateAbbreviation();

        log.info("GeoLocation retrieved Lat{}, Long{}", lat, lon);
        // https://api.open-meteo.com/v1/forecast?latitude=38.678&longitude=-121.1761&daily=temperature_2m_max,temperature_2m_min&current=temperature_2m&timezone=auto&forecast_days=1&temperature_unit=fahrenheit

        var weatherResponse = getWeatherFromApi(lat, lon);
        // add retrieved timestamp to the response as well as place
        Weather weather = new Weather(weatherResponse.currentWeather(), weatherResponse.daily(), place,
                DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        log.info("Retrieved weather, current temperature {} at {}", weather.currentWeather().temperature(),
                weather.currentWeather().time());

        return weather;
    }

    /**
     * Service method to get geolocation details for a zip from Zippopotam
     */
    @Override
    public GeoLocation getGeoDetails(String zip) {

        return restClientBuilder.baseUrl(geoApiUrl + zip).build()
                .get()
                .retrieve()
                .toEntity(GeoLocation.class).getBody();

    }

    /**
     * Service method to retrieve weather details for a given geolocation from Open
     * Mateo
     */
    @Override
    public Weather getWeatherFromApi(String lat, String lon) {

        return restClientBuilder
                .baseUrl(weatherApiUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("current", "temperature_2m")
                        .queryParam("timezone", "auto")
                        .queryParam("temperature_unit", "fahrenheit")
                        .queryParam("daily", "temperature_2m_max,temperature_2m_min")
                        .build())
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(Weather.class).getBody();

    }

}
