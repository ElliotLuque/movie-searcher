package com.izertis.techtestelliot.application.port.out;

import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import reactor.core.publisher.Mono;

public interface MovieCatalog {
     Mono<MoviePage> searchByTitle(String title, int page);
     Mono<Movie> findByImdbId(String imdbId);
}
