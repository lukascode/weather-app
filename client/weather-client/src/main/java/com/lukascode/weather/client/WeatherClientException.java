package com.lukascode.weather.client;

public class WeatherClientException extends RuntimeException {

    public WeatherClientException(String msg) {
        super(msg);
    }

    public WeatherClientException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
