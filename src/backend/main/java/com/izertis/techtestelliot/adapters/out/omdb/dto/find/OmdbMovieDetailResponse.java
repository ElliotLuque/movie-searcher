package com.izertis.techtestelliot.adapters.out.omdb.dto.find;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OmdbMovieDetailResponse(
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("imdbRating") String imdbRating,
        @JsonProperty("Poster") String poster,
        @JsonProperty("Title") String title,
        @JsonProperty("Year") int year,
        @JsonProperty("Director") String director,
        @JsonProperty("Plot") String plot,
        @JsonProperty("Genre") String genre,
        @JsonProperty("Language") String language,
        @JsonProperty("Runtime") String runtime,
        @JsonProperty("Response") String response
) {
}
