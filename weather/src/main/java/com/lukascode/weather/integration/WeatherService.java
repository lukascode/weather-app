package com.lukascode.weather.integration;

import com.lukascode.weather.integration.dto.Weather;
import org.springframework.web.client.RestTemplate;

public class WeatherService {

    private final RestTemplate weatherClient;
    private final String apiKey;

    public WeatherService(String apiKey, RestTemplate weatherClient) {
        this.weatherClient = weatherClient;
        this.apiKey = apiKey;
    }

    public Weather getWeather(Coordinates coordinates) {
        String uri = WeatherUriBuilder
                .of(coordinates)
                .build(apiKey);

        var response = weatherClient.getForEntity(uri, Weather.class);

        return response.getBody();
    }
}
