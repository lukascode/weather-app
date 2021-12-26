package com.lukascode.weather.front.util;

import java.time.LocalDateTime;

public final class DateUtil {

    private static final int NIGHT_BEGINS = 22;
    private static final int NIGHT_ENDS = 6;

    private DateUtil() {}

    public static boolean isNight(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        return hour > NIGHT_BEGINS || hour < NIGHT_ENDS;
    }
}
