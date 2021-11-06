package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sys {

    public final String country;
    public final long sunrise;
    public final long sunset;

    @JsonCreator
    public Sys(@JsonProperty("country") String country,
               @JsonProperty("sunrise") long sunrise,
               @JsonProperty("sunset") long sunset) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
