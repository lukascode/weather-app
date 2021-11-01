package com.lukascode.weather.infrastructure.configuration;

import com.lukascode.weather.integration.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WeatherConfig {

    @Bean
    public WeatherService weatherService(RestTemplate weatherClient,
                                         @Value("${openweather.api.key}") String apiKey) {
        return new WeatherService(apiKey, weatherClient);
    }

    @Bean
    public RestTemplate weatherClient(
            @Value("${openweather.api.uri}") String uri, RestTemplateBuilder builder) {
        return builder
                .rootUri(uri)
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }
}
