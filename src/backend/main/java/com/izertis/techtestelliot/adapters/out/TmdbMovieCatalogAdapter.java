package com.izertis.techtestelliot.adapters.out;

import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "movie.provider", havingValue = "tmdb", matchIfMissing = true)
public class TmdbMovieCatalogAdapter implements MovieCatalog {
    @Qualifier("movieWebClient")
    private final WebClient tmdbWebClient;

    @Override
    public List<Movie> findByTitle(String title) {
        return List.of();
    }

    @Override
    public Optional<Movie> findByImdbId(String imdbId) {
        return Optional.empty();
    }
}
