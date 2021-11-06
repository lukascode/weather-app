package com.lukascode.weather.front.infrastructure.configuration;

public class AppVersion {

    private final String version;

    public AppVersion(String version) {
        this.version = version;
    }

    public String getAppVersion() {
        return version;
    }
}
