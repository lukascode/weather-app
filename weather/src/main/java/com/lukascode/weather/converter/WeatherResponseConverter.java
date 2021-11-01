package com.lukascode.weather.converter;

import com.lukascode.weather.integration.dto.Weather;
import com.lukascode.weather.ws.Clouds;
import com.lukascode.weather.ws.Main;
import com.lukascode.weather.ws.Sys;

public class WeatherResponseConverter {

    public com.lukascode.weather.ws.Weather convert(Weather weather) {
        com.lukascode.weather.ws.Weather weatherDto = new com.lukascode.weather.ws.Weather();
        weatherDto.setVisibility(weather.visibility);
        weatherDto.setMain(getMain(weather));
        weatherDto.setSys(getSys(weather));
        weatherDto.setClouds(getClouds(weather));
        return weatherDto;
    }

    private Main getMain(Weather weather) {
        Main main = new Main();
        main.setTemp(weather.main.temp);
        main.setFeelsLike(weather.main.feelsLike);
        main.setHumidity(weather.main.humidity);
        main.setTempMin(weather.main.tempMin);
        main.setTempMax(weather.main.tempMax);
        return main;
    }

    private Sys getSys(Weather weather) {
        Sys sys = new Sys();
        sys.setSunrise(weather.sys.sunrise);
        sys.setSunset(weather.sys.sunset);
        return sys;
    }

    private Clouds getClouds(Weather weather) {
        Clouds clouds = new Clouds();
        clouds.setAll(weather.clouds.all);
        return clouds;
    }
}
