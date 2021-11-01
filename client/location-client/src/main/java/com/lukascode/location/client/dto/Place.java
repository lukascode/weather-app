package com.lukascode.location.client.dto;

public class Place {

    public final String placeId;
    public final String description;

    public Place(String placeId, String description) {
        this.placeId = placeId;
        this.description = description;
    }
}
