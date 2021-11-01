package com.lukascode.location.integration.placedetails;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.lukascode.location.integration.placedetails.photo.Photo;

import java.util.List;

class PlaceDetailsResponseParser {

    private final String jsonResponse;

    static PlaceDetailsResponseParser of(String jsonResponse) {
        return new PlaceDetailsResponseParser(jsonResponse);
    }

    private PlaceDetailsResponseParser(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    PlaceDetails parse() {
        DocumentContext context = JsonPath.parse(jsonResponse);
        String status = context.read("$.status", String.class);
        String placeId = context.read("$.result.place_id", String.class);
        String formattedAddress = context.read("$.result.formatted_address", String.class);
        Coordinates coordinates = context.read("$.result.geometry.location", Coordinates.class);
        List<Photo> photos = context.read("$.result.photos", new TypeRef<>() {});
        return new PlaceDetails(placeId, formattedAddress, coordinates, photos, status);
    }
}
