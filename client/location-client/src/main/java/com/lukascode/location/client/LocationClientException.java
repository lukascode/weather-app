package com.lukascode.location.client;

public class LocationClientException extends RuntimeException {

    public LocationClientException(String msg) {
        super(msg);
    }

    public LocationClientException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
