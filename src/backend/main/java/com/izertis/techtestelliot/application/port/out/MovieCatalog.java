package com.izertis.techtestelliot.application.port.out;

import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface MovieCatalog {
     Mono<MoviePage> findByTitle(String title, int page);
     Mono<Movie> findByImdbId(String imdbId);
}
