package com.lukascode.location.integration.placedetails;

import com.lukascode.location.integration.placedetails.photo.PhotoResolverService;
import com.lukascode.location.integration.placedetails.timezone.Timezone;
import com.lukascode.location.integration.placedetails.timezone.TimezoneQuery;
import com.lukascode.location.integration.placedetails.timezone.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;

public class PlaceDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceDetailsService.class);

    private final TimezoneService timezoneService;
    private final PhotoResolverService photoResolverService;
    private final RestTemplate googleClient;

    public PlaceDetailsService(TimezoneService timezoneService,
                               PhotoResolverService photoResolverService,
                               RestTemplate googleClient) {
        this.timezoneService = timezoneService;
        this.photoResolverService = photoResolverService;
        this.googleClient = googleClient;
    }

    public Optional<PlaceDetails> getPlaceDetails(PlaceDetailsQuery query) {
        URI uri = PlaceDetailsUriBuilder
                .of(query)
                .build(googleClient.getUriTemplateHandler());

        var response = googleClient.getForEntity(uri, String.class);

        PlaceDetails placeDetails = PlaceDetailsResponseParser
                .of(response.getBody())
                .parse();

        if (!"OK".equals(placeDetails.getStatus())) {
            return Optional.empty();
        }

        LOG.debug("GET {} -> {}|{}", uri, response.getStatusCodeValue(), placeDetails.getStatus());

        placeDetails.setTimezone(getTimezone(placeDetails, query.getLang()));

        var photos = photoResolverService.resolvePhotos(placeDetails.getPhotos());
        placeDetails.setPhotos(photos);

        return Optional.of(placeDetails);
    }

    private Timezone getTimezone(PlaceDetails placeDetails, String lang) {
        TimezoneQuery query = new TimezoneQuery(placeDetails.getCoordinates(), lang, Instant.now());
        return timezoneService.getTimezone(query);
    }
}
