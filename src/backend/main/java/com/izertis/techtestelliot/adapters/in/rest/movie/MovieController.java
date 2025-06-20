package com.izertis.techtestelliot.adapters.in.rest.movie;

import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MoviePageResponse;
import com.izertis.techtestelliot.adapters.in.rest.movie.mapper.MovieMapper;
import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.domain.model.MoviePage;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MoviePageResponse>> searchByTitle(String query, int page) {
        return useCase.searchByTitle(query, page)
                .map(this::toResponse)
                .map(response -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)
                                .cachePublic())
                        .header("Vary", "Accept-Encoding")
                        .body(response));
    }

    @Override
    @GetMapping(value = "/{imdbId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MovieDetailResponse>> findByImdbId(@PathVariable String imdbId) {
        return useCase.findByImdbId(imdbId)
                .map(mapper::toDetail)
                .map(movie -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS)
                                .cachePublic())
                        .header("Vary", "Accept-Encoding")
                        .body(movie))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Helpers
    private MoviePageResponse toResponse(MoviePage page) {
        return new MoviePageResponse(
                page.page(),
                page.totalPages(),
                page.totalElements(),
                page.results().stream()
                        .map(mapper::toPageItem)
                        .toList());
    }
}
