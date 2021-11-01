package com.lukascode.location.integration.autocomplete;

import com.lukascode.location.infrastructure.interfaces.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;

class AutocompleteUriBuilder implements UriBuilder {

    private static final String AUTOCOMPLETE_PATH = "/place/autocomplete/json";

    private final AutocompleteQuery query;

    static AutocompleteUriBuilder of(AutocompleteQuery query) {
        return new AutocompleteUriBuilder(query);
    }

    private AutocompleteUriBuilder(AutocompleteQuery query) {
        this.query = query;
    }

    @Override
    public URI build(UriTemplateHandler uriTemplateHandler) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriTemplateHandler.expand(AUTOCOMPLETE_PATH));
        builder = builder.queryParam("input", query.getInput().trim());
        builder = builder.queryParam("language", query.getLang().trim());
        builder = builder.queryParam("types", "(cities)");
        return builder.build().toUri();
    }
}
