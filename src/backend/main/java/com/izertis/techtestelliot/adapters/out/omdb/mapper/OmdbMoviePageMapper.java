package com.izertis.techtestelliot.adapters.out.omdb.mapper;

import com.izertis.techtestelliot.adapters.out.omdb.dto.search.OmdbMovieSearchResponse;
import com.izertis.techtestelliot.domain.model.MoviePage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OmdbMoviePageMapper {
    int PAGE_SIZE = 10; // OMDB api doesn't let you change page size

    @Mapping(target = "page",          source = "currentPage")
    @Mapping(target = "totalElements",  source = "resp.totalResults", qualifiedByName = "parseLong")
    @Mapping(target = "totalPages",     source = "resp.totalResults", qualifiedByName = "calcPages")
    @Mapping(target = "results",       source = "resp.search")
    MoviePage toDomain(OmdbMovieSearchResponse resp, int currentPage);

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
