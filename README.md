# Weather Application
Spring weather application

## Description

This project is a simple web application that allows users to enter a zip code and view the current temperature and weather in that city. 

The application is built using 
Spring Boot 3.4.4 and Thymeleaf as the page template engine. It uses JDK 21 and
RestClient interact with external APIs. 

The weather data is stored in a local cache and is configured to be cached for 30minutes since the last access.

The Zippopotam US API is utilized to obtain the latitude and longitude coordinates of the given zip code, which are then used to fetch weather data from the Open Mateo Weather API.

## Requirements

- Java 21

## Setup and Installation

1. Clone the repository to your local machine.

2. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

3. The weather application will be accessible at `http://localhost:8080` in the web browser.

## Usage

1. Open the application in your web browser by visiting `http://localhost:8080`.

2. Enter the zip code of the city for which you want to view the weather for.

3. Click the "Submit" button.

4. The application will use the external APIs to fetch the current temperature, High and Low temperatures. The details are displayed on the same index page.
API time i.e the last weather time from Open Mateo API is displayed along with the fetch time. The Open Mateo API only refreshes in 15m intervals. The Last retrieved time is used as indicator for the caching implementation.


## External Free APIs Used

- Zippopotam API: Used to get the latitude and longitude coordinates  along with the city name based on the zip code.
    - API Documentation: [(hhttps://zippopotam.us/)]

- Open Mateo API: Used to get the weather data based on the latitude and longitude coordinates.
    - API Documentation: [Open Mateo API Documentation](https://open-meteo.com/en/docs)

