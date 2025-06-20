package com.izertis.techtestelliot.adapters.in.rest.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Movie page item", description = "Compact representation of a movie used in paginated search results (basic metadata only).")
public record MoviePageItemDTO(
        @Schema(example = "tt0076759") String imdbId,
        @Schema(example = "Star Wars: Episode IV - A New Hope") String title,
        @Schema(example = "1977") int year
) {
}
