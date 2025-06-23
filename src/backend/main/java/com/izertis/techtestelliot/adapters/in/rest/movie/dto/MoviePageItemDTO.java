package com.izertis.techtestelliot.adapters.in.rest.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Movie page item", description = "Compact representation of a movie used in paginated search results (basic metadata only).")
public record MoviePageItemDTO(
        @Schema(example = "tt0076759") String imdbId,
        @Schema(example = "Star Wars: Episode IV - A New Hope") String title,
        @Schema(example = "https://m.media-amazon.com/images/M/MV5BOGUwMDk0Y2MtNjBlNi00NmRiLTk2MWYtMGMyMDlhYmI4ZDBjXkEyXkFqcGc@._V1_SX300.jpg") String imageUrl,
        @Schema(example = "1977") int year
) {
}
