package com.lukascode.location.integration.placedetails.photo;

import com.lukascode.location.infrastructure.interfaces.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;

class PhotoResolverUriBuilder implements UriBuilder {

    private static final String PHOTO_PATH = "/place/photo";

    private final Photo photo;

    static PhotoResolverUriBuilder of(Photo photo) {
        return new PhotoResolverUriBuilder(photo);
    }

    private PhotoResolverUriBuilder(Photo photo) {
        this.photo = photo;
    }

    @Override
    public URI build(UriTemplateHandler uriTemplateHandler) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriTemplateHandler.expand(PHOTO_PATH));
        builder = builder.queryParam("photo_reference", photo.getReference());
        builder = builder.queryParam("maxwidth",photo.getWidth());
        return builder.build().toUri();
    }
}
