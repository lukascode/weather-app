package com.lukascode.location.integration.placedetails.timezone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class TimezoneService {

    private static final Logger LOG = LoggerFactory.getLogger(TimezoneService.class);

    private final RestTemplate googleClient;

    public TimezoneService(RestTemplate googleClient) {
        this.googleClient = googleClient;
    }

    public Timezone getTimezone(TimezoneQuery query) {
        URI uri = TimezoneUriBuilder
                .of(query)
                .build(googleClient.getUriTemplateHandler());

        var response = googleClient.getForEntity(uri, Timezone.class);

        LOG.debug("GET {} -> {}|{}", uri, response.getStatusCodeValue(), response.getBody().getStatus());

        return response.getBody();
    }
}
