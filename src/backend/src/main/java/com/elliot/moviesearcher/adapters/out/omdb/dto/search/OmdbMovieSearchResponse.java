package com.elliot.moviesearcher.adapters.out.omdb.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OmdbMovieSearchResponse(
        @JsonProperty("Search") List<OmdbMovieSearchItemDTO> search,
        @JsonProperty("totalResults") String totalResults,
        @JsonProperty("Response") String response
) {
    public record OmdbMovieSearchItemDTO(
            @JsonProperty("imdbID") String imdbId,
            @JsonProperty("Title") String title,
            @JsonProperty("Poster") String poster,
            @JsonProperty("Year") String year
    ) {
    }
}
