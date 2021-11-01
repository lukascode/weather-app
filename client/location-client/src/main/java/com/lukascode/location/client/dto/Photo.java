package com.lukascode.location.client.dto;

public class Photo {

    public final int width;
    public final int height;
    public final String reference;

    public Photo(int width, int height, String reference) {
        this.width = width;
        this.height = height;
        this.reference = reference;
    }
}
