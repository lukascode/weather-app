package com.lukascode.weather.front.infrastructure.configuration;

import com.lukascode.location.client.LocationClientException;
import com.lukascode.weather.client.WeatherClientException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ResilienceConfig.class);

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(3)
                .slidingWindowSize(3)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(1)
                .recordExceptions(
                        LocationClientException.class,
                        WeatherClientException.class
                )
                .build();
        var circuitBreaker = CircuitBreakerRegistry
                .ofDefaults()
                .circuitBreaker("default", circuitBreakerConfig);
        circuitBreaker.getEventPublisher()
                .onEvent(event -> LOG.debug("Circuit breaker event: {}", event));
        return circuitBreaker;
    }

    @Bean
    public Retry retry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(200))
                .retryExceptions(
                        LocationClientException.class,
                        WeatherClientException.class
                ).build();
        var retry = RetryRegistry.ofDefaults()
                .retry("default", config);
        retry.getEventPublisher()
                .onEvent(event -> LOG.debug("Retry event: {}", event));
        return retry;
    }
}
