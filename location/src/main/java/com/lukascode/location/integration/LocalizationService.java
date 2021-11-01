package com.lukascode.location.integration;

import com.lukascode.location.infrastructure.configuration.caching.CacheConfig;
import com.lukascode.location.integration.autocomplete.AutocompleteQuery;
import com.lukascode.location.integration.autocomplete.AutocompleteService;
import com.lukascode.location.integration.autocomplete.Predictions;
import com.lukascode.location.integration.placedetails.PlaceDetails;
import com.lukascode.location.integration.placedetails.PlaceDetailsQuery;
import com.lukascode.location.integration.placedetails.PlaceDetailsService;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public class LocalizationService {

    private final AutocompleteService autocompleteService;
    private final PlaceDetailsService placeDetailsService;


    public LocalizationService(AutocompleteService autocompleteService,
                               PlaceDetailsService placeDetailsService) {
        this.autocompleteService = autocompleteService;
        this.placeDetailsService = placeDetailsService;
    }

    @Cacheable(cacheNames = CacheConfig.AUTOCOMPLETE_CACHE, key = "#query.input.replaceAll(\"\\s+\",\"\") + #query.lang")
    public Predictions autocomplete(AutocompleteQuery query) {
        return autocompleteService.autocomplete(query);
    }

    @Cacheable(cacheNames = CacheConfig.PLACE_DETAILS_CACHE, key = "#query.placeId + #query.lang")
    public Optional<PlaceDetails> getPlaceDetails(PlaceDetailsQuery query) {
        return placeDetailsService.getPlaceDetails(query);
    }
}
