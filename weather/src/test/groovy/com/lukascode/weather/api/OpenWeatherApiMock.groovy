package com.lukascode.weather.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.lukascode.weather.client.Coordinates
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RootUriTemplateHandler
import org.springframework.web.client.RestTemplate

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

class OpenWeatherApiMock {

    private static final Logger LOG = LoggerFactory.getLogger(OpenWeatherApiMock.class);

    private static final String LOCALHOST = "http://localhost:"

    private WireMockServer server = new WireMockServer(
            wireMockConfig().dynamicPort()
    )

    private RestTemplate weatherClient

    OpenWeatherApiMock(RestTemplate weatherClient) {
        this.weatherClient = weatherClient
    }

    void mockGetWeather(Coordinates coordinates) {
        String pattern = String.format("/\\?lat=%s&lon=%s.*\$", coordinates.lat, coordinates.lon)
        server.stubFor(get(urlMatching(pattern))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody("""
                            {
                                "coord": {
                                    "lon": 18.5452,
                                    "lat": 54.3387
                                },
                                "weather": [
                                    {
                                        "id": 800,
                                        "main": "Clear",
                                        "description": "clear sky",
                                        "icon": "01n"
                                    }
                                ],
                                "base": "stations",
                                "main": {
                                    "temp": 9.38,
                                    "feels_like": 7.44,
                                    "temp_min": 8.7,
                                    "temp_max": 12.03,
                                    "pressure": 1010,
                                    "humidity": 77
                                },
                                "visibility": 10000,
                                "wind": {
                                    "speed": 3.6,
                                    "deg": 160
                                },
                                "clouds": {
                                    "all": 0
                                },
                                "dt": 1635714869,
                                "sys": {
                                    "type": 2,
                                    "id": 2042574,
                                    "country": "PL",
                                    "sunrise": 1635659124,
                                    "sunset": 1635693198
                                },
                                "timezone": 3600,
                                "id": 3094976,
                                "name": "Kowale",
                                "cod": 200
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
            LOG.debug("OpenWeatherApiMock server started on port {}", server.port())
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
        weatherClient.setUriTemplateHandler(new RootUriTemplateHandler(rootUri));
    }
}
