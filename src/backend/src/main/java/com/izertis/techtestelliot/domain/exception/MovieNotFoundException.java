package com.izertis.techtestelliot.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String imdbId) {
        super("Movie with IMDB ID " + imdbId + " not found");
    }
}
