package com.izertis.techtestelliot.adapters.in.rest;

import com.izertis.techtestelliot.adapters.in.rest.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.dto.MoviePageResponse;
import com.izertis.techtestelliot.adapters.in.rest.mapper.MovieMapper;
import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.domain.model.MoviePage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final QueryMovieUseCase useCase;
    private final MovieMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MoviePageResponse>> searchByTitle(
            @RequestParam @NotBlank(message = "Query can't be empty") String query,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Page number must be 1 or greater") int page) {
        return useCase.searchByTitle(query, page)
                .map(this::toResponse)
                .map(response -> ResponseEntity.ok()
                        .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)
                                .cachePublic())
                        .header("Vary", "Accept-Encoding")
                        .body(response));
    }

    @GetMapping(value = "/{imdbId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<MovieDetailResponse>> findByImdbId(
            @PathVariable
            @NotBlank(message = "IMD ID can't be empty")
            @Pattern(regexp = "^tt\\d{7,}$", message = "IMD ID must start with tt followed by atleast 7 digits")
              String imdbId) {
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
