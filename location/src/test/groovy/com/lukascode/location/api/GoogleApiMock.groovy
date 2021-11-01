package com.lukascode.location.api

import com.github.tomakehurst.wiremock.WireMockServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RootUriTemplateHandler
import org.springframework.web.client.RestTemplate

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

class GoogleApiMock {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleApiMock.class);

    private static final String LOCALHOST = "http://localhost:"

    private WireMockServer server = new WireMockServer(
            wireMockConfig().dynamicPort()
    )

    private RestTemplate googleClient

    GoogleApiMock(RestTemplate googleClient) {
        this.googleClient = googleClient
    }

    void mockAutocomplete() {
        server.stubFor(get(urlPathEqualTo("/place/autocomplete/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody("""
                            {
                              "status": "OK",
                              "predictions": [
                                {
                                  "place_id": "place1",
                                  "description": "Warsaw, Poland"
                                },
                                {
                                  "place_id": "place2",
                                  "description": "New York, USA"
                                }
                              ]
                            }
                        """)
                ))
    }

    void reset() {
        server.resetAll()
    }

    @PostConstruct
    void startServer() {
        if (server != null && !server.isRunning()) {
            server.start();
            LOG.debug("GoogleApiMock server started on port {}", server.port())
            changeUriForClient()
        }
    }

    @PreDestroy
    void stopServer() {
        if (server != null && server.isRunning()) {
            server.stop();
        }
    }

    private void changeUriForClient() {
        String rootUri = LOCALHOST.concat(Integer.toString(server.port()))
        googleClient.setUriTemplateHandler(new RootUriTemplateHandler(rootUri));
    }
}
