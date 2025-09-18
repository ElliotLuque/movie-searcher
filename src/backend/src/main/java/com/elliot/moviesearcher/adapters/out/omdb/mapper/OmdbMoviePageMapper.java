package com.elliot.moviesearcher.adapters.out.omdb.mapper;

import com.elliot.moviesearcher.adapters.out.omdb.dto.search.OmdbMovieSearchResponse;
import com.elliot.moviesearcher.domain.model.Movie;
import com.elliot.moviesearcher.domain.model.MoviePage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OmdbMoviePageMapper {
    int PAGE_SIZE = 10; // OMDB api doesn't let you change page size

    @Mapping(target = "page",          source = "currentPage")
    @Mapping(target = "pageSize",       constant = "10")
    @Mapping(target = "totalElements",  source = "response.totalResults", qualifiedByName = "parseLong")
    @Mapping(target = "totalPages",     source = "response.totalResults", qualifiedByName = "calcPages")
    @Mapping(target = "results",       source = "response.search")
    MoviePage toDomain(OmdbMovieSearchResponse response, int currentPage);

    @Mapping(target = "imageUrl", source = "poster")
    Movie toDomain(OmdbMovieSearchResponse.OmdbMovieSearchItemDTO resp);

    // Helpers
    @Named("parseLong")
    default long parseLong(String s) {
        try { return Long.parseLong(s.replaceAll("\\D", "")); }
        catch (Exception e) { return 0L; }
    }

    @Named("calcPages")
    default int calcTotalPages(String totalResults) {
        long total = parseLong(totalResults);
        return (int) Math.ceil(total / (double) PAGE_SIZE);
    }}
