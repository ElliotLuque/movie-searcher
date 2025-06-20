package com.izertis.techtestelliot.adapters.in.rest;

import com.izertis.techtestelliot.adapters.in.rest.dto.MovieDetailResponse;
import com.izertis.techtestelliot.adapters.in.rest.dto.MoviePageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Tag(name = "Movies", description = "Search and retrieve movie information")
public interface MovieControllerDoc {

    @Operation(summary = "Search movies by title",
            description = "Returns a paginated list of movies whose titles match the given query.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movies matching query found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MoviePageResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameter",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples  = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Bad request",
                                                        "status": 400,
                                                        "detail": "searchByTitle.query: Query can't be empty",
                                                        "instance": "/api/v1/movies"
                                                      }
                                                    """
                                    )
                            )
                    )
            }
    )
    Mono<ResponseEntity<MoviePageResponse>> searchByTitle(
            @Parameter(description = "Full or partial movie title to search for", required = true, example = "Star Wars")
            @RequestParam @NotBlank(message = "Query can't be empty")
            String query,

            @Parameter(description = "Page number to return (must be 1 or greater)", example = "1")
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Page number must be 1 or greater")
            int page
    );

    @Operation(summary = "Get movie details by IMDB ID",
            description = "Returns detailed information about the movie with the given IMDB ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Movie found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MovieDetailResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Movie not found",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples  = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Not Found",
                                                        "status": 404,
                                                        "detail": "Movie with IMDB ID tt007675924 not found",
                                                        "instance": "/api/v1/movies/tt007675924"
                                                      }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameter",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class),
                                    examples  = @ExampleObject(
                                            value = """
                                                    {
                                                        "type": "about:blank",
                                                        "title": "Bad request",
                                                        "status": 400,
                                                        "detail": "findByImdbId.imdbId: IMDB ID must start with tt followed by atleast 7 digits",
                                                        "instance": "/api/v1/movies/50"
                                                      }
                                                    """
                                    )
                            )
                    )
            }
    )
    Mono<ResponseEntity<MovieDetailResponse>> findByImdbId(
            @Parameter(description = "IMDB ID of the movie to retrieve", required = true, example = "tt0076759")
            @NotBlank(message = "IMDB ID can't be empty")
            @Pattern(regexp = "^tt\\d{7,}$", message = "IMDB ID must start with tt followed by atleast 7 digits")
            String imdbId);
}
