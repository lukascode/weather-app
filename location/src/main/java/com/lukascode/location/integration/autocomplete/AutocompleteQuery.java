package com.lukascode.location.integration.autocomplete;

public class AutocompleteQuery {

    private String input;

    private String lang;

    public AutocompleteQuery(String input, String lang) {
        this.input = input;
        this.lang = lang;
    }

    public String getInput() {
        return input;
    }

    public String getLang() {
        return lang;
    }
}
