package com.elliot.moviesearcher.adapters.in.rest.movie;

import com.elliot.moviesearcher.adapters.in.rest.movie.dto.MovieDetailResponse;
import com.elliot.moviesearcher.adapters.in.rest.movie.dto.MoviePageResponse;
import com.elliot.moviesearcher.adapters.in.rest.movie.mapper.MovieMapper;
import com.elliot.moviesearcher.application.port.in.QueryMovieUseCase;
import com.elliot.moviesearcher.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController implements MovieControllerDoc {

    private final QueryMovieUseCase useCase;
    private final MovieMapper mapper;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MoviePageResponse>> searchByTitle(String query, int page) {
        return useCase.searchByTitle(query, page)
                .map(this::toResponse)
                .map(response -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES)
                                .cachePrivate()
                                .mustRevalidate()
                        )
                        .header("Vary", "Cookie")
                        .body(response));
    }

    @Override
    @GetMapping(value = "/{imdbId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MovieDetailResponse>> findByImdbId(@PathVariable String imdbId) {
        return useCase.findByImdbId(imdbId)
                .map(mapper::toDetail)
                .map(movie -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES)
                                .cachePrivate()
                                .cachePublic()
                        )
                        .header("Vary", "Cookie")
                        .body(movie))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Helpers
    private MoviePageResponse toResponse(MoviePage page) {
        return new MoviePageResponse(
                page.page(),
                page.pageSize(),
                page.totalPages(),
                page.totalElements(),
                page.results().stream()
                        .map(mapper::toPageItem)
                        .toList());
    }
}
