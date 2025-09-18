package com.elliot.moviesearcher.application.service;

import com.elliot.moviesearcher.application.port.in.QueryMovieUseCase;
import com.elliot.moviesearcher.application.port.out.MovieCatalog;
import com.elliot.moviesearcher.domain.model.Movie;
import com.elliot.moviesearcher.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SearchMovieService implements QueryMovieUseCase {
    private final MovieCatalog movieCatalog;

    @Override
    public Mono<MoviePage> searchByTitle(String title, int page) {
        return movieCatalog.searchByTitle(title, page);
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        return movieCatalog.findByImdbId(imdbId);
    }
}
