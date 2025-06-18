package com.izertis.techtestelliot.application.port.in;

import com.izertis.techtestelliot.domain.model.Movie;

import java.util.List;
import java.util.Optional;

public interface QueryMovieUseCase {
    List<Movie> searchByTitle(String title);
    Optional<Movie> findByImdbId(String imdbId);
}
