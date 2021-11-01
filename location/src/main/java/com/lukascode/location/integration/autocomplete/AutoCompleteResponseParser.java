package com.lukascode.location.integration.autocomplete;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;

import java.util.List;

class AutoCompleteResponseParser {

    private final String jsonResponse;

    static AutoCompleteResponseParser of(String jsonResponse) {
        return new AutoCompleteResponseParser(jsonResponse);
    }

    private AutoCompleteResponseParser(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    Predictions parse() {
        DocumentContext context = JsonPath.parse(jsonResponse);
        String status = context.read("$.status", String.class);
        List<Place> places = context.read("$.predictions[*]", new TypeRef<>() {});
        return new Predictions(places, status);
    }
}
