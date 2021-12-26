package com.lukascode.weather.front.client;

import com.lukascode.location.client.LocationClient;
import com.lukascode.location.client.dto.PlaceDetails;
import com.lukascode.location.client.dto.Predictions;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;

import java.util.Optional;

public class ResilientLocationClient extends LocationClient {

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public ResilientLocationClient(String host, CircuitBreaker circuitBreaker, Retry retry) {
        super(host);
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
    }

    @Override
    public Predictions autocomplete(String input, String lang) {
        return Decorators.ofSupplier(() -> super.autocomplete(input, lang))
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry).get();
    }

    @Override
    public Optional<PlaceDetails> getPlaceDetails(String placeId, String lang) {
        return Decorators.ofSupplier(() -> super.getPlaceDetails(placeId, lang))
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry).get();
    }
}
