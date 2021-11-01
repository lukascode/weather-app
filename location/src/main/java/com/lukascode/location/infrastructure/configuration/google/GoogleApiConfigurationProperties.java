package com.lukascode.location.infrastructure.configuration.google;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@Configuration
@ConfigurationProperties(prefix = "google.api")
class GoogleApiConfigurationProperties {

    @NotEmpty
    private String uri;

    @NotEmpty
    private String key;

    void setUri(String uri) {
        this.uri = uri;
    }

    void setKey(String key) {
        this.key = key;
    }

    String getUri() {
        return uri;
    }

    String getKey() {
        return key;
    }
}
