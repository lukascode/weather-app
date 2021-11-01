package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {

    public final double speed; // meter / sec

    @JsonCreator
    public Wind(@JsonProperty("speed") double speed) {
        this.speed = speed;
    }
}
