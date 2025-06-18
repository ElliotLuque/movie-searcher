package com.izertis.techtestelliot.application.service;

import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SearchMovieService implements QueryMovieUseCase {
    private final MovieCatalog movieCatalog;

    @Override
    public Mono<MoviePage> searchByTitle(String title, int page) {
        return movieCatalog.findByTitle(title, page);
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        return movieCatalog.findByImdbId(imdbId);
    }
}
