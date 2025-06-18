package com.izertis.techtestelliot.adapters.out.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieDetailResponse(
        long id,
        String title,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("vote_average") double voteAverage,
        String overview,
        int runtime,
        List<Genre> genres,
        @JsonProperty("original_language") String originalLanguage
) {
    public record Genre(
      int id,
      String name
    ){}
}
