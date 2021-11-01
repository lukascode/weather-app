package com.lukascode.location.api

import com.lukascode.location.LocalizationApp
import com.lukascode.location.client.LocationClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@Import(GoogleTestApiConfig)
@ActiveProfiles("it")
@SpringBootTest(
        classes = LocalizationApp,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LocalizationApiSpec extends Specification {

    private static final String HOST = "http://localhost:";

    private static final String TEST_LANG = "en"
    private static final String TEST_INPUT = "Warsaw"

    @LocalServerPort
    private int port;

    @Autowired
    private GoogleApiMock googleApiMock

    private LocationClient locationClient;

    def setup() {
        googleApiMock.reset()
        locationClient = new LocationClient(HOST + port)
    }

    def "should return predictions"() {
        given:
        googleApiMock.mockAutocomplete()

        when:
        def response = locationClient.autocomplete(TEST_INPUT, TEST_LANG)

        then:
        response != null
        with(response) {
            status == "OK"
            places.size() == 2
        }
    }
}
