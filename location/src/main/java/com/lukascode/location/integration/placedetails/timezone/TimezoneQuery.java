package com.lukascode.location.integration.placedetails.timezone;

import com.lukascode.location.integration.placedetails.Coordinates;

import java.time.Instant;

public class TimezoneQuery {

    private Coordinates coordinates;
    private String lang;
    private Instant timestamp;

    public TimezoneQuery(Coordinates coordinates, String lang, Instant timestamp) {
        this.coordinates = coordinates;
        this.lang = lang;
        this.timestamp = timestamp;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getLang() {
        return lang;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
