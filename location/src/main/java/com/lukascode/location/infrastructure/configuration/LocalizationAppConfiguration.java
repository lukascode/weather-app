package com.lukascode.location.infrastructure.configuration;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import com.lukascode.location.integration.LocalizationService;
import com.lukascode.location.integration.autocomplete.AutocompleteService;
import com.lukascode.location.integration.placedetails.PlaceDetailsService;
import com.lukascode.location.integration.placedetails.photo.PhotoResolverService;
import com.lukascode.location.integration.placedetails.timezone.TimezoneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;
import java.util.Set;

@Configuration
public class LocalizationAppConfiguration {

    static {
        com.jayway.jsonpath.Configuration.setDefaults(new com.jayway.jsonpath.Configuration.Defaults() {

            private final JsonProvider jsonProvider = new GsonJsonProvider();
            private final MappingProvider mappingProvider = new GsonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.of(
                        Option.DEFAULT_PATH_LEAF_TO_NULL,
                        Option.SUPPRESS_EXCEPTIONS
                );
            }
        });
    }

    @Bean
    public LocalizationService localizationService(RestTemplate googleClient) {
        return new LocalizationService(
                new AutocompleteService(googleClient),
                new PlaceDetailsService(
                        new TimezoneService(googleClient),
                        new PhotoResolverService(googleClient),
                        googleClient
                )
        );
    }
}
