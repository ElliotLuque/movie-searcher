package com.izertis.techtestelliot.adapters.out.tmdb;

import com.izertis.techtestelliot.adapters.out.tmdb.mapper.TmdbMovieDetailMapper;
import com.izertis.techtestelliot.adapters.out.tmdb.mapper.TmdbMovieMapper;
import com.izertis.techtestelliot.adapters.out.tmdb.mapper.TmdbPageMapper;
import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "movies.provider", havingValue = "tmdb", matchIfMissing = true)
public class TmdbMovieCatalogAdapter implements MovieCatalog {
    private final @Qualifier("tmdbWebClient") WebClient client;
    private final TmdbPageMapper pageMapper;
    private final TmdbMovieMapper movieMapper;
    private final TmdbMovieDetailMapper movieDetailMapper;

    @Override
    public Mono<MoviePage> findByTitle(String title, int page) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .build()
                )
                .retrieve()
                .bodyToMono(TmdbMovieSearchResponse.class)
                .map(resp -> pageMapper.toDomain(resp, page));
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
