package com.lukascode.weather.integration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Main {

    public final double temp;
    public final double feelsLike;
    public final double tempMin;
    public final double tempMax;
    public final int humidity;

    @JsonCreator
    public Main(@JsonProperty("temp") double temp,
                @JsonProperty("feels_like") double feelsLike,
                @JsonProperty("temp_min") double tempMin,
                @JsonProperty("temp_max") double tempMax,
                @JsonProperty("humidity") int humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
    }
}
