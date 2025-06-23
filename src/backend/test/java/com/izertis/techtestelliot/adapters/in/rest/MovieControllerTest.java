package com.izertis.techtestelliot.adapters.in.rest;

import com.izertis.techtestelliot.adapters.in.rest.movie.MovieController;
import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MoviePageItemDTO;
import com.izertis.techtestelliot.adapters.in.rest.movie.dto.MoviePageResponse;
import com.izertis.techtestelliot.adapters.in.rest.movie.mapper.MovieMapper;
import com.izertis.techtestelliot.application.port.in.QueryMovieUseCase;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@WebFluxTest(MovieController.class)
@RequiredArgsConstructor
public class MovieControllerTest {

    private final WebTestClient client;
    private final QueryMovieUseCase useCase;
    private final MovieMapper mapper;

    @Test
    void shouldReturnMoviePageWhenQueryIsValid() {
        var movie = new Movie("tt1234567", "Star Test", 2020, 8.0, "Director", "Plot", "English", 120, List.of("Action"));
        var moviePage = new MoviePage(1, 1, 1, List.of(movie));

        Mockito.when(useCase.searchByTitle("star", 1)).thenReturn(Mono.just(moviePage));

        client.get()
                .uri("/api/v1/movies?query=star")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.movies[0].title").isEqualTo("Star Test");
    }
}
