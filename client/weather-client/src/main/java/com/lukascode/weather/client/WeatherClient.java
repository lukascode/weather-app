package com.lukascode.weather.client;

import com.lukascode.weather.ws.Weather;
import com.lukascode.weather.ws.WeatherService;
import com.lukascode.weather.ws.WeatherServiceWS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Objects;

public class WeatherClient {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherClient.class);

    private static final String WEBSERVICE = "/ws/WeatherService?wsdl";
    private final WeatherServiceWS weatherServiceWS;

    public WeatherClient(String host) {
        Objects.requireNonNull(host);
        try {
            URL wsdlURL = new URL(host + WEBSERVICE);
            weatherServiceWS = new WeatherService(wsdlURL).getWeatherServiceWS();
            LOG.debug("Weather client created successfully");
        } catch (Exception e) {
            throw new WeatherClientException("Cannot create weather client", e);
        }
    }

    public Weather fetchWeather(Coordinates coordinates) {
        LOG.debug("Fetching weather for coordinates {lat: {}, lon: {}}", coordinates.lat, coordinates.lon);
        com.lukascode.weather.ws.Coordinates coord =
                new com.lukascode.weather.ws.Coordinates();
        coord.setLat(coordinates.lat);
        coord.setLon(coordinates.lon);
        return weatherServiceWS.getWeather(coord);
    }
}
