package com.lukascode.location.integration.autocomplete;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("place_id")
    private String placeId;

    private String description;

    public Place(String placeId, String description) {
        this.placeId = placeId;
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getDescription() {
        return description;
    }
}
