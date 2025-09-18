package com.elliot.moviesearcher.adapters.in.rest.movie.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Movie page", description = "Paginated wrapper that contains the current page index, total counts and a list of movie items.")
public record MoviePageResponse(
        @Schema(description = "Current page index (1-based)", example = "1", minimum = "1") int page,
        @Schema(description = "Number of elements per page", example = "10") int pageSize,
        @Schema(description = "Total number of pages available to query", example = "5") int totalPages,
        @Schema(description = "Total number of elements that match the query", example = "663")int totalElements,
        @ArraySchema(
                schema      = @Schema(
                        implementation = MoviePageItemDTO.class
                ),
                arraySchema = @Schema(
                        description = "List of movies contained in this page",
                        example     = "[{ \"imdbId\": \"tt0076759\", \"title\": \"Star Wars: Episode IV - A New Hope\", \"year\": \"1977\" }, { \"imdbId\": \"tt0080684\", \"title\": \"Star Wars: Episode V - The Empire Strikes Back\", \"year\": \"1980\" }]"
                ),
                minItems = 0
        )
        List<MoviePageItemDTO> results
) {
}
