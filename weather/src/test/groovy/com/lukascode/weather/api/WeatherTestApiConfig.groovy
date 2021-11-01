package com.lukascode.weather.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
class WeatherTestApiConfig {

    @Bean
    OpenWeatherApiMock openWeatherApiMock(RestTemplate weatherClient) {
        return new OpenWeatherApiMock(weatherClient);
    }
}
