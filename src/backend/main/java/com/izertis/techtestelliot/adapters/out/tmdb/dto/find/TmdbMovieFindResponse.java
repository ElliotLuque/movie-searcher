package com.izertis.techtestelliot.adapters.out.tmdb.dto.find;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieFindResponse(
        @JsonProperty("movie_results") List<TmdbMovieFindItemResponse> movieResults
) {
    public record  TmdbMovieFindItemResponse(
            @JsonProperty("id") int id
    ) {}
}
