package com.izertis.techtestelliot.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String imdbId) {
        super("Movie with IMDb ID " + imdbId + " not found");
    }
}
