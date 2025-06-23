package com.izertis.techtestelliot.adapters.out.tmdb.dto.find;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieDetailResponse(
        @JsonProperty("imdb_id") String imdbId,
        @JsonProperty("title") String title,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("vote_average") double voteAverage,
        @JsonProperty("overview") String overview,
        @JsonProperty("runtime") int runtime,
        @JsonProperty("genres") List<Genre> genres,
        @JsonProperty("original_language") String originalLanguage,
        @JsonProperty("credits") Credits credits,
        @JsonProperty("external_ids") ExternalIds externalIds
) {
    public record ExternalIds (
            @JsonProperty("imdb_id") String imdbId
    ) {}

    public record Genre(
      int id,
      String name
    ){}

    public record Credits(
            List<CrewMember> crew
    ) {
        public record CrewMember(
                String job,
                String name
        ) {}
    }
}
