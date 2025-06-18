package com.izertis.techtestelliot.adapters.out.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OmdbMovieSearchItemDTO(
        @JsonProperty("imdbID") String imdbId,
        @JsonProperty("Title") String title,
        @JsonProperty("Year") String year
) {
}
