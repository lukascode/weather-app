package com.lukascode.location.client.dto;

import java.util.List;

public class PlaceDetails {

    public final String placeId;
    public final String formattedAddress;
    public final Coordinates coordinates;
    public final Timezone timezone;
    public final List<Photo> photos;
    public final String status;

    public PlaceDetails(String placeId,
                        String formattedAddress,
                        Coordinates coordinates,
                        Timezone timezone,
                        List<Photo> photos,
                        String status) {
        this.placeId = placeId;
        this.formattedAddress = formattedAddress;
        this.coordinates = coordinates;
        this.timezone = timezone;
        this.photos = photos;
        this.status = status;
    }
}
