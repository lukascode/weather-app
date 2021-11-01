package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {

    public final long all; // cloudiness %

    @JsonCreator
    public Clouds(@JsonProperty("all") long all) {
        this.all = all;
    }
}
