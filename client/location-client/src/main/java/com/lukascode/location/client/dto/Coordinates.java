package com.lukascode.location.client.dto;

public class Coordinates {

    public final double lat;
    public final double lng;

    public Coordinates(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }
}
