package com.lukascode.location.integration.placedetails.photo;

import com.google.gson.annotations.SerializedName;

public class Photo {

    private int width;

    private int height;

    @SerializedName("photo_reference")
    private String reference;

    public Photo(int width, int height, String reference) {
        this.width = width;
        this.height = height;
        this.reference = reference;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getReference() {
        return reference;
    }
}
