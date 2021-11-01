package com.lukascode.weather.api;

import com.lukascode.weather.converter.WeatherResponseConverter;
import com.lukascode.weather.integration.WeatherService;
import com.lukascode.weather.ws.Coordinates;
import com.lukascode.weather.ws.Weather;
import com.lukascode.weather.ws.WeatherServiceWS;
import org.apache.cxf.annotations.SchemaValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(
        targetNamespace = "http://lukascode.com/WeatherService",
        serviceName = "WeatherService",
        portName = "WeatherServiceWS",
        endpointInterface = "com.lukascode.weather.ws.WeatherServiceWS"
)
@SchemaValidation(type = SchemaValidation.SchemaValidationType.BOTH)
public class WeatherEndpoint implements WeatherServiceWS {

    private final WeatherResponseConverter weatherConverter = new WeatherResponseConverter();

    @Autowired
    private WeatherService weatherService;

    @Override
    public Weather getWeather(Coordinates coordinates) {
        var weather = weatherService.getWeather(new com.lukascode.weather.integration
                .Coordinates(coordinates.getLat(), coordinates.getLon()));
        return weatherConverter.convert(weather);
    }
}
