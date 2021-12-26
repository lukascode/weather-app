package com.lukascode.weather.front.client;

import com.lukascode.weather.client.Coordinates;
import com.lukascode.weather.client.WeatherClient;
import com.lukascode.weather.ws.Weather;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;

public class ResilientWeatherClient extends WeatherClient {

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public ResilientWeatherClient(String host, CircuitBreaker circuitBreaker, Retry retry) {
        super(host);
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
    }

    public Weather fetchWeather(Coordinates coordinates) {
        return Decorators.ofSupplier(() -> super.fetchWeather(coordinates))
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry).get();
    }
}
