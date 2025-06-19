package com.izertis.techtestelliot.adapters.out.tmdb.dto.find;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.izertis.techtestelliot.adapters.out.tmdb.dto.TmdbMovieSearchResponse;

import java.util.List;

public record TmdbMovieFindResponse(
        @JsonProperty("movie_results") List<TmdbMovieFindItemResponse> movieResults
) {
    public record  TmdbMovieFindItemResponse(
            @JsonProperty("id") long id
    ) {}
}
