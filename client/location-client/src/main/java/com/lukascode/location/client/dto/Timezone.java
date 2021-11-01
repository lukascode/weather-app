package com.lukascode.location.client.dto;

public class Timezone {

    public final String timeZoneId;
    public final String timeZoneName;

    public Timezone(String timeZoneId, String timeZoneName) {
        this.timeZoneId = timeZoneId;
        this.timeZoneName = timeZoneName;
    }
}
