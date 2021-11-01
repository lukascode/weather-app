package com.lukascode.location.infrastructure.configuration.google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

class GoogleApiKeyInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleApiKeyInterceptor.class);
    private static final String KEY_PARAM_NAME = "key";
    private final String apiKey;

    GoogleApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        LOG.trace("Intercepting {} {}", request.getMethod().name(), request.getURI());
        URI uri = UriComponentsBuilder.fromHttpRequest(request)
                .queryParam(KEY_PARAM_NAME, apiKey).build().toUri();
        HttpRequest rq = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uri;
            }
        };
        return execution.execute(rq, body);
    }
}
