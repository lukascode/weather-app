package com.lukascode.location.infrastructure.interfaces;

import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;

public interface UriBuilder {

    URI build(UriTemplateHandler uriTemplateHandler);

}
