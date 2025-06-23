package com.izertis.techtestelliot.adapters.in.rest.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "Movie detail", description = "Full movie information including runtime, plot, ratings, and production details.")
public record MovieDetailResponse(
        @Schema(example = "tt0076759") String imdbId,
        @Schema(example = "Star Wars: Episode IV - A New Hope") String title,
        @Schema(example = "https://m.media-amazon.com/images/M/MV5BOGUwMDk0Y2MtNjBlNi00NmRiLTk2MWYtMGMyMDlhYmI4ZDBjXkEyXkFqcGc@._V1_SX300.jpg") String imageUrl,
        @Schema(example = "1977") int year,
        @Schema(example = "George Lucas") String director,
        @Schema(example = "8.6") double rating,
        @Schema(example = "The Imperial Forces, under orders from cruel Darth Vader...") String plot,
        @Schema(type = "array", example = "Action, Adventure, Fantasy") List<String> genres,
        @Schema(example = "English") String language,
        @Schema(example = "121") int runtime
) {
}
