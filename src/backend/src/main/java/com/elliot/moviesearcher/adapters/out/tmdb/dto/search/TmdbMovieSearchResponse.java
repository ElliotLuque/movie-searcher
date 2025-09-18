package com.elliot.moviesearcher.adapters.out.tmdb.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieSearchResponse(
        @JsonProperty("page") int page,
        @JsonProperty("total_pages") int totalPages,
        @JsonProperty("total_results") int totalResults,
        @JsonProperty("results") List<TmdbMovieSearchItemDTO> results
) {
    public record TmdbMovieSearchItemDTO(
            int id
    ) { }
}
