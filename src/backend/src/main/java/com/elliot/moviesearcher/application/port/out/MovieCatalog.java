package com.elliot.moviesearcher.application.port.out;

import com.elliot.moviesearcher.domain.model.Movie;
import com.elliot.moviesearcher.domain.model.MoviePage;
import reactor.core.publisher.Mono;

public interface MovieCatalog {
     Mono<MoviePage> searchByTitle(String title, int page);
     Mono<Movie> findByImdbId(String imdbId);
}
