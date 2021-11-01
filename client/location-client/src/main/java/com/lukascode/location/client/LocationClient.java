package com.lukascode.location.client;

import com.lukascode.location.client.dto.PlaceDetails;
import com.lukascode.location.client.dto.Predictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocationClient {

    private static final Logger LOG = LoggerFactory.getLogger(LocationClient.class);

    private static final String BASE_PATH = "/localization";
    private final RestTemplate httpClient;

    public LocationClient(String host) { // eg. http://localhost:8080
        Objects.requireNonNull(host);
        httpClient = new RestTemplate();
        httpClient.setMessageConverters(List.of(new GsonHttpMessageConverter()));
        httpClient.setUriTemplateHandler(new DefaultUriBuilderFactory(host + BASE_PATH));
    }


    public Predictions autocomplete(String input, String lang) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(lang);
        String url = String.format("/autocomplete?input=%s&lang=%s", input, lang);
        return get(url, Predictions.class);
    }

    public Optional<PlaceDetails> getPlaceDetails(String placeId, String lang) {
        Objects.requireNonNull(placeId);
        Objects.requireNonNull(lang);
        String url = String.format("/places/%s?lang=%s", placeId, lang);
        return Optional.ofNullable(get(url, PlaceDetails.class));
    }

    @Nullable
    private <T> T get(String url, Class<T> clazz) {
        String responseMsg = "Location service respond with ";
        try {
            ResponseEntity<T> response = httpClient.getForEntity(url, clazz);
            if (LOG.isDebugEnabled()) {
                LOG.debug(responseMsg.concat("status: {}"), response.getStatusCodeValue());
            }
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(responseMsg.concat("status: {}"), e.getRawStatusCode());
            }
            if (404 == e.getRawStatusCode()) {
                return null;
            }
            throw new LocationClientException(responseMsg.concat("an error"), e);
        } catch (RestClientException e) {
            throw new LocationClientException(responseMsg.concat("an error"), e);
        }
    }
}
