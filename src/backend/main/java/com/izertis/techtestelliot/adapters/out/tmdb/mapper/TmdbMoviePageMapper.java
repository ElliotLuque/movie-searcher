package com.izertis.techtestelliot.adapters.out.tmdb.mapper;

import com.izertis.techtestelliot.adapters.out.tmdb.dto.search.TmdbMovieSearchResponse;
import com.izertis.techtestelliot.domain.model.Movie;
import com.izertis.techtestelliot.domain.model.MoviePage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TmdbMoviePageMapper {
    @Mapping(target = "page", source = "response.page")
    @Mapping(target = "pageSize", constant = "20")
    @Mapping(target = "totalPages", source = "response.totalPages")
    @Mapping(target = "totalElements", source = "response.totalResults")
    @Mapping(target = "results", source = "movies")
    MoviePage toDomain(TmdbMovieSearchResponse response, List<Movie> movies);
}
