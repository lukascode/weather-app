package com.lukascode.weather.client;

import com.lukascode.weather.ws.Weather;
import com.lukascode.weather.ws.WeatherService;
import com.lukascode.weather.ws.WeatherServiceWS;

import java.net.URL;
import java.util.Objects;

public class WeatherClient {

    private static final String WEBSERVICE = "/ws/WeatherService?wsdl";
    private final WeatherServiceWS weatherServiceWS;

    public WeatherClient(String host) {
        Objects.requireNonNull(host);
        try {
            URL wsdlURL = new URL(host + WEBSERVICE);
            weatherServiceWS = new WeatherService(wsdlURL).getWeatherServiceWS();
        } catch (Exception e) {
            throw new WeatherClientException("Cannot create weather client", e);
        }
    }

    public Weather fetchWeather(Coordinates coordinates) {
        com.lukascode.weather.ws.Coordinates coord =
                new com.lukascode.weather.ws.Coordinates();
        coord.setLat(coordinates.lat);
        coord.setLon(coordinates.lon);
        return weatherServiceWS.getWeather(coord);
    }
}
