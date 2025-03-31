package com.weatherapp.weatherdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import com.weatherapp.weatherdemo.model.Address;
import com.weatherapp.weatherdemo.model.openmateo.Weather;
import com.weatherapp.weatherdemo.service.IWeatherService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class WeatherController {
    private final IWeatherService weatherService;

    @GetMapping("/")
    public String home(final Address address) {
        return "index";
    }

    @PostMapping("/checkWeather")
    public String checkWeather(
            final @Valid Address address,
            final BindingResult bindingResult,
            final Model model) 
    {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors {}", bindingResult.getAllErrors());
            return "index";
        }
        Weather weatherResponse = weatherService.getWeather(address.getZip());
        model.addAttribute("response", weatherResponse);
        return "index";

    }

}
