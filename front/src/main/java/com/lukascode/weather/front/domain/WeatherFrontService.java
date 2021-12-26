package com.lukascode.weather.front.domain;

import com.lukascode.location.client.LocationClient;
import com.lukascode.location.client.dto.PlaceDetails;
import com.lukascode.location.client.dto.Predictions;
import com.lukascode.weather.client.Coordinates;
import com.lukascode.weather.client.WeatherClient;
import com.lukascode.weather.front.util.DateUtil;
import com.lukascode.weather.front.web.Location;
import com.lukascode.weather.front.web.WeatherAndLocationDetails;
import com.lukascode.weather.ws.Weather;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WeatherFrontService {

    private static final String DEFAULT_LANG = "en";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final LocationClient locationClient;
    private final WeatherClient weatherClient;

    public WeatherFrontService(LocationClient locationClient, WeatherClient weatherClient) {
        this.locationClient = locationClient;
        this.weatherClient = weatherClient;
    }

    public List<Location> getLocations(String query) {
        Predictions predictions = locationClient.autocomplete(query, DEFAULT_LANG);
        return predictions.places
                .stream()
                .map(p -> new Location(p.placeId, p.description))
                .collect(Collectors.toList());
    }

    public Optional<WeatherAndLocationDetails> getWeather(String placeId) {
        Optional<PlaceDetails> placeDetails = locationClient.getPlaceDetails(placeId, DEFAULT_LANG);
        Optional<Weather> weather = placeDetails
                .map(p -> p.coordinates)
                .map(c -> new Coordinates(c.lat, c.lng))
                .map(weatherClient::fetchWeather);
        return Optional.of(Pair.of(placeDetails, weather))
                .filter(p -> p.getLeft().isPresent())
                .filter(p -> p.getRight().isPresent())
                .map(p -> Pair.of(p.getLeft().get(), p.getRight().get()))
                .map(this::convert);
    }

    private WeatherAndLocationDetails convert(Pair<PlaceDetails, Weather> weatherAndLocation) {
        Weather weather = weatherAndLocation.getRight();
        PlaceDetails placeDetails = weatherAndLocation.getLeft();
        LocalDateTime sunrise = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(weather.getSys().getSunrise()),
                ZoneId.of(placeDetails.timezone.timeZoneId));
        LocalDateTime sunset = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(weather.getSys().getSunset()),
                ZoneId.of(placeDetails.timezone.timeZoneId));
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(placeDetails.timezone.timeZoneId));
        return new WeatherAndLocationDetails(placeDetails,
                new WeatherAndLocationDetails.PlaceWeather(
                        Math.round(weather.getMain().getTemp()),
                        weather.getMain().getFeelsLike(),
                        weather.getMain().getTempMin(),
                        weather.getMain().getTempMax(),
                        weather.getMain().getHumidity(),
                        weather.getVisibility(),
                        weather.getClouds().getAll(),
                        weather.getWind().getSpeed(),
                        weather.getSys().getCountry(),
                        sunrise.format(TIME_FORMATTER),
                        sunset.format(TIME_FORMATTER)
                ), currentTime.format(TIME_FORMATTER),
                DateUtil.isNight(currentTime));
    }
}
