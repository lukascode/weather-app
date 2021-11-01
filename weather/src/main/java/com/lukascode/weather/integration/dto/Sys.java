package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sys {

    public final long sunrise;
    public final long sunset;

    @JsonCreator
    public Sys(@JsonProperty("sunrise") long sunrise,
               @JsonProperty("sunset") long sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
