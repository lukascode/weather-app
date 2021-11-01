package com.lukascode.location.integration.placedetails;

import com.lukascode.location.infrastructure.interfaces.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;

class PlaceDetailsUriBuilder implements UriBuilder {

    private static final String PLACE_DETAILS_PATH = "/place/details/json";

    private final PlaceDetailsQuery query;

    static PlaceDetailsUriBuilder of(PlaceDetailsQuery query) {
        return new PlaceDetailsUriBuilder(query);
    }

    private PlaceDetailsUriBuilder(PlaceDetailsQuery query) {
        this.query = query;
    }

    @Override
    public URI build(UriTemplateHandler uriTemplateHandler) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriTemplateHandler.expand(PLACE_DETAILS_PATH));
        builder = builder.queryParam("place_id", query.getPlaceId());
        builder = builder.queryParam("language", query.getLang().trim());
        return builder.build().toUri();
    }
}
