package com.weatherapp.weatherdemo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.weatherdemo.model.zippopotam.GeoLocation;

@RestClientTest(WeatherService.class)
public class WeatherServiceTest {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void resetMockServer(){
        mockRestServiceServer.reset();
    }

    @Test
    void getGeoDetails_WithBadZip_Returns404() {

        this.mockRestServiceServer.expect(requestTo("https://api.zippopotam.us/us/9563"))
			.andRespond(withResourceNotFound().body("{}"));
        
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            this.weatherService.getGeoDetails("9563");
        });       
    }

    @Test
    void getGeoDetails_WithGoodZip() throws IOException {
        String geoLocationJson;
        ClassPathResource resource = new ClassPathResource("data/geoLocation.json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            geoLocationJson = FileCopyUtils.copyToString(reader);
        }

        this.mockRestServiceServer.expect(requestTo("https://api.zippopotam.us/us/95636"))
			.andRespond(withSuccess(geoLocationJson, MediaType.APPLICATION_JSON));
        
        GeoLocation location = this.weatherService.getGeoDetails("95636");
        assertThat(location.places().get(0).placeName(),  is(equalTo("Grizzly Flats")));
           
    }
    
}
