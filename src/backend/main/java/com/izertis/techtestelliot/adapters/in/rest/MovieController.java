package com.izertis.techtestelliot.adapters.in.rest;

import com.izertis.techtestelliot.adapters.in.rest.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.dto.MoviePageResponse;
import com.izertis.techtestelliot.adapters.in.rest.mapper.MovieMapper;
import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final QueryMovieUseCase useCase;
    private final MovieMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<MoviePageResponse>> searchByTitle(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page) {
        return useCase.searchByTitle(query, page)
                .map(this::toResponse)
                .map(response -> {
                    String etag = DigestUtils.md5Hex(response.toString());

                    return ResponseEntity.ok()
                            .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)
                                    .cachePublic()
                                    .staleWhileRevalidate(Duration.ofHours(1)))
                            .eTag(etag)
                            .header("Vary", "Accept-Encoding")
                            .body(response);
                });
    }
    @GetMapping("/{imdbId}")
    public Mono<ResponseEntity<MovieDetailResponse>> findByImdbId(@PathVariable String imdbId) {
        return useCase.findByImdbId(imdbId)
                .map(mapper::toDetail)
                .map(movie -> {
                    String etag = DigestUtils.md5Hex(movie.toString());

                    return ResponseEntity.ok()
                            .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS)
                                    .cachePublic()
                                    .mustRevalidate())
                            .eTag(etag)
                            .header("Vary", "Accept-Encoding")
                            .body(movie);
                })
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
