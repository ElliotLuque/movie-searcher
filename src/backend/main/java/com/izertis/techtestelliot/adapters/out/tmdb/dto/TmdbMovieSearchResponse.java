package com.izertis.techtestelliot.adapters.out.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieSearchResponse(
        int page,
        @JsonProperty("total_pages") int totalPages,
        @JsonProperty("total_results") int totalResults
) {
}
