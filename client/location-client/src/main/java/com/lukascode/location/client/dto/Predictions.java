package com.lukascode.location.client.dto;

import java.util.List;

public class Predictions {

    public final String status;
    public final List<Place> places;

    public Predictions(List<Place> places, String status) {
        this.status = status;
        this.places = places;
    }
}
