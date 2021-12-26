package com.lukascode.weather.front.infrastructure.configuration;

import com.lukascode.location.client.LocationClient;
import com.lukascode.weather.client.WeatherClient;
import com.lukascode.weather.front.client.ResilientLocationClient;
import com.lukascode.weather.front.client.ResilientWeatherClient;
import com.lukascode.weather.front.domain.WeatherFrontService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public WeatherFrontService weatherFrontService(LocationClient locationClient, WeatherClient weatherClient) {
        return new WeatherFrontService(locationClient, weatherClient);
    }

    @Bean
    public LocationClient locationClient(
            @Value("${external-apis.location}") String locationService,
            CircuitBreaker circuitBreaker, Retry retry) {
        return new ResilientLocationClient(locationService, circuitBreaker, retry);
    }

    @Bean
    public WeatherClient weatherClient(
            @Value("${external-apis.weather}") String weatherService,
            CircuitBreaker circuitBreaker, Retry retry) {
        return new ResilientWeatherClient(weatherService, circuitBreaker, retry);
    }

    @Bean
    public AppVersion appVersion(@Value("${info.build.version}") String version) {
        return new AppVersion(version);
    }
}
