package com.lukascode.weather.front.util;

public final class CountryFlagUtil {

    private CountryFlagUtil() {}

    public static String getCountryFlag(String country) {
        country = country.toUpperCase();

        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
    }
}
