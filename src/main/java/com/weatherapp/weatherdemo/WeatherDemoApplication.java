package com.weatherapp.weatherdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class WeatherDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherDemoApplication.class, args);
	}

}
