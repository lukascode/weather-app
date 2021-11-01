package com.lukascode.location.integration.placedetails;

public class PlaceDetailsQuery {

    private String placeId;

    private String lang;

    public PlaceDetailsQuery(String placeId, String lang) {
        this.placeId = placeId;
        this.lang = lang;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getLang() {
        return lang;
    }
}
