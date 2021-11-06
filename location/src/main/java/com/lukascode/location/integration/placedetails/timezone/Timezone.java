package com.lukascode.location.integration.placedetails.timezone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Timezone {

    private String timeZoneId;

    private String timeZoneName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String status;

    @JsonCreator
    public Timezone(@JsonProperty("timeZoneId") String timeZoneId,
                    @JsonProperty("timeZoneName") String timeZoneName,
                    @JsonProperty("status") String status) {
        this.timeZoneId = timeZoneId;
        this.timeZoneName = timeZoneName;
        this.status = status;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public String getStatus() {
        return status;
    }
}
