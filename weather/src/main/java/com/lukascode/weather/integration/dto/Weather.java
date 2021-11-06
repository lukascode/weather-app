package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {

    public final Sys sys;
    public final Main main;
    public final Clouds clouds;
    public final Wind wind;
    public final int visibility;

    @JsonCreator
    public Weather(@JsonProperty("sys") Sys sys,
                   @JsonProperty("main") Main main,
                   @JsonProperty("clouds") Clouds clouds,
                   @JsonProperty("visibility") int visibility,
                   @JsonProperty("wind") Wind wind) {
        this.sys = sys;
        this.main = main;
        this.clouds = clouds;
        this.visibility = visibility;
        this.wind = wind;
    }
}
