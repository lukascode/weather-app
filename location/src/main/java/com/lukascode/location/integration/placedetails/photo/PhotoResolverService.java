package com.lukascode.location.integration.placedetails.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class PhotoResolverService {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoResolverService.class);

    private final RestTemplate googleClient;

    public PhotoResolverService(RestTemplate googleClient) {
        this.googleClient = googleClient;
    }

    public List<Photo> resolvePhotos(List<Photo> photos) {
        return photos.parallelStream()
                .map(this::resolvePhoto)
                .collect(Collectors.toList());
    }

    private Photo resolvePhoto(Photo photo) {
        URI uri = PhotoResolverUriBuilder
                .of(photo)
                .build(googleClient.getUriTemplateHandler());

        var response = googleClient.getForEntity(uri, Void.class);

        LOG.debug("GET {} -> {}", uri, response.getStatusCodeValue());

        return new Photo(photo.getWidth(), photo.getHeight(),
                response.getHeaders().getLocation().toString());
    }
}
