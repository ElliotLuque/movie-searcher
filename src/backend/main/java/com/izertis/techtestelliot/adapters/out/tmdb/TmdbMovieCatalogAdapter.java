package com.izertis.techtestelliot.adapters.out.tmdb;

import com.izertis.techtestelliot.adapters.out.tmdb.dto.search.TmdbMovieSearchResponse;
import com.izertis.techtestelliot.adapters.out.tmdb.dto.find.TmdbMovieDetailResponse;
import com.izertis.techtestelliot.adapters.out.tmdb.dto.find.TmdbMovieFindResponse;
import com.izertis.techtestelliot.adapters.out.tmdb.mapper.TmdbMovieMapper;
import com.izertis.techtestelliot.adapters.out.tmdb.mapper.TmdbMoviePageMapper;
import com.izertis.techtestelliot.application.port.out.MovieCatalog;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "movies.provider", havingValue = "tmdb", matchIfMissing = true)
public class TmdbMovieCatalogAdapter implements MovieCatalog {
    private final @Qualifier("tmdbWebClient") WebClient client;
    private final TmdbMoviePageMapper pageMapper;
    private final TmdbMovieMapper movieMapper;

    @Override
    @Cacheable(value = "moviesByTitle", key = "#title + '-' + #page")
    public Mono<MoviePage> searchByTitle(String title, int page) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .queryParam("page", page)
                        .build()
                )
                .retrieve()
                .bodyToMono(TmdbMovieSearchResponse.class)
                .flatMap(searchResponse -> {
                    if (searchResponse.results().isEmpty()) {
                        return Mono.just(pageMapper.toDomain(searchResponse, List.of()));
                    }

                    return Flux.fromIterable(searchResponse.results())
                            .flatMap(movieResult ->
                                    getMovieFromTmdbId(movieResult.id())
                                            .onErrorResume(e -> Mono.empty())
                            )
                            .filter(movie -> movie.imdbId() != null)
                            .collectList()
                            .map(movies -> pageMapper.toDomain(searchResponse, movies));
                });
    }

    @Override
    @Cacheable(value = "moviesById", key = "#imdbId")
    public Mono<Movie> findByImdbId(String imdbId) {
        return client.get()
                .uri("/find/{id}?external_source=imdb_id", imdbId)
                .retrieve()
                .bodyToMono(TmdbMovieFindResponse.class)
                .flatMap(resp -> resp.movieResults().stream().findFirst()
                        .map(Mono::just).orElseGet(Mono::empty))
                .flatMap(tmdbDto ->
                        client.get()
                                .uri("/movie/{id}?append_to_response=credits", tmdbDto.id())
                                .retrieve()
                                .bodyToMono(TmdbMovieDetailResponse.class)
                                .map(movieMapper::toDomain)
                );
    }

    // Helpers
    private Mono<Movie> getMovieFromTmdbId(int tmdbId) {
        return client.get()
                .uri("/movie/{id}?append_to_response=external_ids", tmdbId)
                .retrieve()
                .bodyToMono(TmdbMovieDetailResponse.class)
                .map(movieMapper::toDomain);
    }
}
