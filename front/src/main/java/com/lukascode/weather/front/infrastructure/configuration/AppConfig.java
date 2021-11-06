package com.lukascode.weather.front.infrastructure.configuration;

import com.lukascode.location.client.LocationClient;
import com.lukascode.weather.client.WeatherClient;
import com.lukascode.weather.front.domain.WeatherFrontService;
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
    public LocationClient locationClient(@Value("${external-apis.location}") String locationService) {
        return new LocationClient(locationService);
    }

    @Bean
    public WeatherClient weatherClient(@Value("${external-apis.weather}") String weatherService) {
        return new WeatherClient(weatherService);
    }

    @Bean
    public AppVersion appVersion(@Value("${info.build.version}") String version) {
        return new AppVersion(version);
    }
}
