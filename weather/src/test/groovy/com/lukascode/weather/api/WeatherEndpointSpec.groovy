package com.lukascode.weather.api

import com.lukascode.weather.WeatherApp
import com.lukascode.weather.client.Coordinates
import com.lukascode.weather.client.WeatherClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@Import(WeatherTestApiConfig)
@ActiveProfiles("it")
@SpringBootTest(
        classes = WeatherApp,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class WeatherEndpointSpec extends Specification {

    private static final String HOST = "http://localhost:"

    @LocalServerPort
    private int port;

    @Autowired
    private OpenWeatherApiMock openWeatherApiMock;

    private WeatherClient weatherClient

    def setup() {
        openWeatherApiMock.reset()
        weatherClient = new WeatherClient(HOST + port)
    }

    def "should fetch weather properly"() {
        given:
        def coordinates = new Coordinates(54.3387, 18.5452)
        openWeatherApiMock.mockGetWeather(coordinates)

        when:
        def weather = weatherClient.fetchWeather(coordinates)

        then:
        weather != null
        with(weather) {
            sys.sunrise == 1635659124
            sys.sunset == 1635693198
            main.temp == 9.38
            main.feelsLike == 7.44
            main.tempMin == 8.7
            main.tempMax == 12.03
            main.humidity == 77
            clouds.all == 0
            visibility == 10000
        }
    }
}
