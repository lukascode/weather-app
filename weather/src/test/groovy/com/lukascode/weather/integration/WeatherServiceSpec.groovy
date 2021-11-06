package com.lukascode.weather.integration

import com.lukascode.weather.integration.dto.Clouds
import com.lukascode.weather.integration.dto.Main
import com.lukascode.weather.integration.dto.Sys
import com.lukascode.weather.integration.dto.Weather
import com.lukascode.weather.integration.dto.Wind
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject

class WeatherServiceSpec extends Specification {

    private static final String TEST_API_KEY = "eeb93e9c-5e65-4d44-b123-9881e61e9e0d"
    private RestTemplate weatherClient

    @Subject
    private WeatherService weatherService

    def setup() {
        weatherClient = Mock(RestTemplate)
        weatherService = new WeatherService(TEST_API_KEY, weatherClient)
    }

    def "should get weather properly"() {
        given:
        def coordinates = new Coordinates(54.3387, 18.5452 )
        weatherClient.getForEntity(
                { it.toString().contains("lat=54.3387&lon=18.5452") } as String,
                Weather.class
        ) >> prepareTestWeather()

        when:
        def weather = weatherService.getWeather(coordinates)

        then:
        weather != null
        weather.visibility == 10000
    }

    private static ResponseEntity<Weather> prepareTestWeather() {
        def weather = new Weather(
                new Sys("PL", 1635659124, 1635693198),
                new Main(9.38, 7.44, 8.7, 12.03, 77),
                new Clouds(0),
                10000,
                new Wind(4)
        )
        return new ResponseEntity<Weather>(weather, HttpStatus.OK)
    }
}
