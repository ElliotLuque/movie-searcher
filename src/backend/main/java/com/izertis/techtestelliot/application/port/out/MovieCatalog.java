package com.izertis.techtestelliot.application.port.out;

import com.izertis.techtestelliot.domain.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieCatalog {
     List<Movie> findByTitle(String title);
     Optional<Movie> findByImdbId(String imdbId);
}
