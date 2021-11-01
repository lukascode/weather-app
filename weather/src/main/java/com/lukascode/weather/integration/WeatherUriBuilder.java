package com.lukascode.weather.integration;

class WeatherUriBuilder {

    private final Coordinates coordinates;

    static WeatherUriBuilder of(Coordinates coordinates) {
        return new WeatherUriBuilder(coordinates);
    }

    private WeatherUriBuilder(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    String build(String apiKey) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("/")
                .append("?lat=").append(coordinates.lat)
                .append("&lon=").append(coordinates.lng)
                .append("&appid=").append(apiKey)
                .append("&units=metric")
                .append("&lang=en");
        return builder.toString();
    }
}
