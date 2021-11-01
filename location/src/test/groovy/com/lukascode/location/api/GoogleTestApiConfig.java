package com.lukascode.location.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class GoogleTestApiConfig {

    @Bean
    public GoogleApiMock googleApiMock(RestTemplate googleClient) {
        return new GoogleApiMock(googleClient);
    }
}
