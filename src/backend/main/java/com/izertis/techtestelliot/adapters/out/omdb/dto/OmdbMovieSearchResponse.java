package com.izertis.techtestelliot.adapters.out.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OmdbMovieSearchResponse(
        @JsonProperty("Search") List<OmdbMovieSearchItemDTO> search,
        @JsonProperty("totalResults") String totalResults
        ) {
}
