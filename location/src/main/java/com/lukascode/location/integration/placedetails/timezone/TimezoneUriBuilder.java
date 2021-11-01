package com.lukascode.location.integration.placedetails.timezone;

import com.lukascode.location.infrastructure.interfaces.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;

class TimezoneUriBuilder implements UriBuilder {

    private static final String TIMEZONE_PATH = "/timezone/json";

    private final TimezoneQuery query;

    static TimezoneUriBuilder of(TimezoneQuery query) {
        return new TimezoneUriBuilder(query);
    }

    private TimezoneUriBuilder(TimezoneQuery query) {
        this.query = query;
    }

    @Override
    public URI build(UriTemplateHandler uriTemplateHandler) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriTemplateHandler.expand(TIMEZONE_PATH));
        builder = builder.queryParam("location", query.getCoordinates());
        builder = builder.queryParam("timestamp", query.getTimestamp().getEpochSecond());
        builder = builder.queryParam("language", query.getLang());
        return builder.build().toUri();
    }
}
