package com.lukascode.location.infrastructure.configuration.caching;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PLACE_DETAILS_CACHE = "PlaceDetails";
    public static final String AUTOCOMPLETE_CACHE = "Autocomplete";

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new CaffeineCache(AUTOCOMPLETE_CACHE, caffeine(ticker)),
                new CaffeineCache(PLACE_DETAILS_CACHE, caffeine(ticker))
        ));
        return cacheManager;
    }

    private Cache caffeine(Ticker ticker) {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(60))
                .maximumSize(500)
                .ticker(ticker)
                .build();
    }
}
