package com.lukascode.location.integration.autocomplete;

import java.util.List;

public class Predictions {

    private String status;

    private List<Place> places;

    public Predictions(List<Place> places, String status) {
        this.status = status;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public List<Place> getPlaces() {
        return places;
    }
}
