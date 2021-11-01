package com.lukascode.weather.integration

import spock.lang.Specification

class WeatherUriBuilderSpec extends Specification {

    private static final Coordinates COORDINATES = new Coordinates(54.3387, 18.5452)
    private static final String TEST_API_KEY = "eeb93e9c-5e65-4d44-b123-9881e61e9e0d"

    def "should build uri properly"() {
        given:
        def builder = WeatherUriBuilder.of(COORDINATES)

        when:
        def uri = builder.build(TEST_API_KEY)

        then:
        uri == "/?lat=54.3387&lon=18.5452&appid=eeb93e9c-5e65-4d44-b123-9881e61e9e0d&units=metric&lang=en"
    }
}
