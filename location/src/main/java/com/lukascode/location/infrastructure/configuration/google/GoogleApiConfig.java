package com.lukascode.location.infrastructure.configuration.google;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GoogleApiConfig {

    @Bean
    public RestTemplate googleClient(GoogleApiConfigurationProperties props, RestTemplateBuilder builder) {
        return builder
                .rootUri(props.getUri())
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .additionalInterceptors(new GoogleApiKeyInterceptor(props.getKey()))
                .requestFactory(GoogleClientHttpRequestFactory.class)
                .build();
    }
}
