package com.izertis.techtestelliot.application.service;

import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchMovieService implements QueryMovieUseCase {
    private final MovieCatalog movieCatalog;

    @Override
    public List<Movie> searchByTitle(String title) {
        return movieCatalog.findByTitle(title);
    }

    @Override
    public Optional<Movie> findByImdbId(String imdbId) {
        return movieCatalog.findByImdbId(imdbId);
    }
}
