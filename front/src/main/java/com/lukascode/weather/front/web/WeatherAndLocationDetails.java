package com.lukascode.weather.front.web;

import com.lukascode.location.client.dto.PlaceDetails;

public class WeatherAndLocationDetails {

    public final PlaceDetails placeDetails;
    public final PlaceWeather placeWeather;
    public final String currentTime;
    public final boolean isNight;

    public WeatherAndLocationDetails(PlaceDetails placeDetails,
                                     PlaceWeather placeWeather,
                                     String currentTime,
                                     boolean isNight) {
        this.placeDetails = placeDetails;
        this.placeWeather = placeWeather;
        this.currentTime = currentTime;
        this.isNight = isNight;
    }

    public static class PlaceWeather {
        public final double temp;
        public final double feelsLike;
        public final double tempMin;
        public final double tempMax;
        public final int humidity;
        public final int visibility;
        public final long clouds;
        public final double windSpeed;
        public final String country;
        public final String sunriseTime;
        public final String sunsetTime;

        public PlaceWeather(double temp, double feelsLike, double tempMin, double tempMax,
                            int humidity, int visibility, long clouds, double windSpeed,
                            String country, String sunriseTime, String sunsetTime) {
            this.temp = temp;
            this.feelsLike = feelsLike;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.humidity = humidity;
            this.visibility = visibility;
            this.clouds = clouds;
            this.windSpeed = windSpeed;
            this.country = country;
            this.sunriseTime = sunriseTime;
            this.sunsetTime = sunsetTime;
        }
    }
}
