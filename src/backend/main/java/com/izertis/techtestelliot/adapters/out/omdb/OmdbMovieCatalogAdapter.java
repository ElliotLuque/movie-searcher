package com.izertis.techtestelliot.adapters.out.omdb;

import com.izertis.techtestelliot.adapters.out.omdb.dto.find.OmdbMovieDetailResponse;
import com.izertis.techtestelliot.adapters.out.omdb.dto.search.OmdbMovieSearchResponse;
import com.izertis.techtestelliot.adapters.out.omdb.mapper.OmdbMovieMapper;
import com.izertis.techtestelliot.adapters.out.omdb.mapper.OmdbMoviePageMapper;
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
@ConditionalOnProperty(name = "movies.provider", havingValue = "omdb")
public class OmdbMovieCatalogAdapter implements MovieCatalog {
    private final @Qualifier("omdbWebClient") WebClient client;
    private final OmdbMovieMapper movieMapper;
    private final OmdbMoviePageMapper pageMapper;

    @Override
    public Mono<MoviePage> searchByTitle(String title, int page) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("s", title)
                        .queryParam("type", "movie")
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieSearchResponse.class)
                .map(resp -> pageMapper.toDomain(resp, page));
    }

    @Override
    public Mono<Movie> findByImdbId(String imdbId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("i", imdbId)
                        .queryParam("plot", "full")
                        .build())
                .retrieve()
                .bodyToMono(OmdbMovieDetailResponse.class)
                .map(movieMapper::toDomain);
    }
}
