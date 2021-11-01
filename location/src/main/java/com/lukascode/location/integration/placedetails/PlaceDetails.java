package com.lukascode.location.integration.placedetails;

import com.lukascode.location.integration.placedetails.photo.Photo;
import com.lukascode.location.integration.placedetails.timezone.Timezone;

import java.util.List;

public class PlaceDetails {

    private String placeId;

    private String formattedAddress;

    private Coordinates coordinates;

    private Timezone timezone;

    private List<Photo> photos;

    private String status;

    public PlaceDetails(String placeId,
                        String formattedAddress,
                        Coordinates coordinates,
                        List<Photo> photos,
                        String status) {
        this.placeId = placeId;
        this.formattedAddress = formattedAddress;
        this.coordinates = coordinates;
        this.photos = photos;
        this.status = status;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getStatus() {
        return status;
    }
}
