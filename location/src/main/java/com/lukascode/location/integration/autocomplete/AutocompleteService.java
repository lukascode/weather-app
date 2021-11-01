package com.lukascode.location.integration.autocomplete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class AutocompleteService {

    private static final Logger LOG = LoggerFactory.getLogger(AutocompleteService.class);

    private final RestTemplate googleClient;

    public AutocompleteService(RestTemplate googleClient) {
        this.googleClient = googleClient;
    }

    public Predictions autocomplete(AutocompleteQuery query) {
        URI uri = AutocompleteUriBuilder
                .of(query)
                .build(googleClient.getUriTemplateHandler());

        var response = googleClient.getForEntity(uri, String.class);

        Predictions predictions = AutoCompleteResponseParser
                .of(response.getBody())
                .parse();

        LOG.debug("GET {} -> {}|{}", uri, response.getStatusCodeValue(), predictions.getStatus());

        return predictions;
    }
}
