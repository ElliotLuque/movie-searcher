package com.izertis.techtestelliot.adapters.out.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OmdbMovieDetailResponse(
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("imdbRating") String imdbRating,
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year,
        @JsonProperty("Director") String director,
        @JsonProperty("Plot") String plot,
        @JsonProperty("Genre") String genre,
        @JsonProperty("Language") String language,
        @JsonProperty("Runtime") String runtime
) {
}
